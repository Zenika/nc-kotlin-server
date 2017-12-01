import { makeLog } from "./log";

import {
  startContainers,
  stopContainers,
} from "./docker";

import { startRouter } from "./router";

const log = makeLog("☠");

async function start() {
  try {
    await startContainers();
    startRouter();
  } catch (error) {
     log("Unexpected error", error);
     stop(1);
  }
}

async function stop(exitCode = 0) {
  await stopContainers();
  process.exit(exitCode);
}

process.on("SIGINT", async () => stop(130));

start();
