import { resolve } from "path";

import {
  map,
} from "lodash";

export interface ILanguage {
  key: string;
  extension: string;
  imageName: string;
  imageTag: string;
  runCmd?: string;
  execCmd: string[];
  tempDir?: string;
  volumes?: string[];
}

export interface IConfig {
  languages: ILanguage[];
}

const configPath = process.env.CONFIG_PATH || resolve(process.cwd(), "config.json");

let config: IConfig;

export async function getConfig(): Promise<IConfig> {
  return config || (config = await import(configPath));
}

export async function getLanguages() {
  const { languages } = await getConfig();
  return languages;
}

export async function getLanguageNames() {
  const languages = await getLanguages();
  return map(languages, ({ key }) => key);
}
