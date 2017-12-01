import {
  mkdtempSync,
  writeFileSync,
} from "fs";

import {
  resolve,
} from "path";

import {
  Duplex,
  PassThrough,
} from "stream";

import {
  chain,
  head,
  identity,
  keyBy,
  map,
  mapValues,
  replace,
  trimStart,
} from "lodash";

import { Container } from "node-docker-api/lib/container";

import * as uuid from "uuid/v4";

import { getLanguages, ILanguage } from "../config";
import { makeLog } from "../log";

import { docker } from "./docker";
import { ensureImage } from "./images";
import { promisifyStream } from "./utils";

const log = makeLog("📦");

const containers: { [key: string]: {
  container: Container,
  language: ILanguage,
  externalTempDir: string,
  internalTempDir: string,
} } = {};

const rootTmpDir = process.env.TMPDIR || `${process.cwd()}/tmp`;
const rootTmpDirVolume = process.env.TMPDIR_VOLUME || rootTmpDir;

export async function startContainers() {
  log("Starting containers...");

  const languages = await getLanguages();

  await chain(languages)
    .map((language) => ensureContainer(language))
    .map(async (container) => addContainer(await container))
    .map(async (container) => startContainer(await container))
    .thru((promises) => Promise.all(promises))
    .value();

  log("All containers started");
}

async function ensureContainer(language: ILanguage): Promise<[ILanguage, Container]> {
  const container = await getContainer(language.key);

  if (container) {
    log(`Found existing container ${getContainerName(container)} for ${language.key} language`);
    return [language, container];
  }

  return [language, await createContainer(language)];
}

async function getContainer(language: string) {
  return head(await docker.container.list({
    all: true,
    filters: {
      label: ["codehero", `language=${language}`],
    },
  }));
}

async function createContainer(language: ILanguage): Promise<Container> {
  log(`Creating new container for ${language.key} language...`);

  await ensureImage(language.imageName, language.imageTag);

  const externalTempDir = mkdtempSync(`${rootTmpDir}/codehero-${language.key}-`);
  const internalRootTempDir = language.tempDir || "/tmp";
  const internalTempDir = `${internalRootTempDir}${replace(externalTempDir, RegExp(`^${rootTmpDir}`), '')}`;

  const options: any = {
    HostConfig: {
      Binds: [
        `${rootTmpDirVolume}:${internalRootTempDir}:ro`,
      ],
      CapAdd: ["DAC_READ_SEARCH"],
      CapDrop: ["ALL"],
      ReadonlyRootfs: true,
    },
    Image: `${language.imageName}:${language.imageTag}`,
    Labels: {
      codehero: "true",
      externalTempDir,
      internalTempDir,
      language: language.key,
    },
    StopSignal: "SIGKILL",
    Tty: true,
  };

  if (language.runCmd) {
    options.Cmd = language.runCmd;
  }
  if (language.volumes) {
    options.Volumes = mapValues(keyBy(language.volumes, identity), () => ({}));
  }

  await docker.container.create(options);

  const container = await getContainer(language.key);

  log(`Container ${getContainerName(container)} created for language ${language.key}`);

  return container;
}

function addContainer([language, container]: [ILanguage, Container]): Container {
  containers[language.key] = {
    container,
    externalTempDir: getContainerLabel(container, "externalTempDir"),
    internalTempDir: getContainerLabel(container, "internalTempDir"),
    language,
  };
  return container;
}

async function startContainer(container: Container): Promise<Container> {
  log(`(Re)Starting container ${getContainerName(container)}`);
  return container.restart();
}

export async function execCode(languageKey: string, { code, input = "" }: { code: string, input?: string }) {
  const {
    container,
    language: { extension, execCmd },
    externalTempDir,
    internalTempDir,
  } = await containers[languageKey];

  const file = `${uuid()}${extension}`;

  writeFileSync(resolve(externalTempDir, file), code);

  const Cmd = [...execCmd, `${internalTempDir}/${file}`];

  const options: any = {
    AttachStderr: true,
    AttachStdin: true,
    AttachStdout: true,
    Cmd,
  };

  const exec = await container.exec.create(options);

  const stream = await exec.start({
    Detach: false,
    hijack: true,
  }) as Duplex;

  const stdout = new PassThrough();
  const stderr = new PassThrough();
  exec.modem.demuxStream(stream, stdout, stderr);

  let out = "";
  let err = "";

  stdout.on("data", (data: Buffer) => {
    out += data.toString("utf8");
  });
  stderr.on("data", (data: Buffer) => {
    err += data.toString("utf8");
  });

  stream.end(input);

  await promisifyStream(stream);

  const exitCode: number = (await exec.status()).data["ExitCode"]; // tslint:disable-line:no-string-literal

  return {
    err,
    exitCode,
    out,
  };
}

export async function stopContainers() {
  log("Stopping containers...");

  await Promise.all(map(containers, stopContainer));

  log("All containers stopped");
}

async function stopContainer({ container }: { container: Container }): Promise<Container> {
  log(`Stopping container ${getContainerName(container)}`);
  return container.stop();
}

function getContainerName(container: Container): string {
  const names: string[] = container.data["Names"]; // tslint:disable-line:no-string-literal
  return trimStart(names[0], "/");
}

function getContainerLabels(container: Container): { [key: string]: string } {
  return container.data["Labels"]; // tslint:disable-line:no-string-literal
}

function getContainerLabel(container: Container, key: string) {
  return getContainerLabels(container)[key];
}
