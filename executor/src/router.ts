import * as bodyParser from "body-parser";
import * as express from "express";

import {
  makeLog,
} from "./log";

import {
    getLanguages,
} from "./config";

import {
  execCode,
} from "./docker";

const log = makeLog("🔀");

export function startRouter() {
  const app = express();

  app.use(bodyParser.json());

  app.get("/language", async (req, res) => {
    res.send(await getLanguages());
  });

  app.post("/language/:language", async (req, res) => {
    res.send(await execCode(req.params.language, req.body));
  });

  app.listen(3000, () => {
    log("Listening on http://localhost:3000/");
  });
}
