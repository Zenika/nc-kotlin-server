import {
  head,
  noop,
} from "lodash";

import {
  makeLog,
} from "../log";

import { docker } from "./docker";
import { promisifyStream } from "./utils";

const log = makeLog("🐳");

export async function ensureImage(imageName: string, imageTag: string) {
  const images = await docker.image.list({ filter: `${imageName}:${imageTag}` });

  if (head(images)) { return; }

  log(`Pulling ${imageName}:${imageTag} image...`);

  const stream = await docker.image.create({}, { fromImage: imageName, tag: imageTag });

  await promisifyStream(stream, noop);

  log(`${imageName}:${imageTag} image successfully pulled`);
}
