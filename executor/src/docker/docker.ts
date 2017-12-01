import { Docker } from "node-docker-api";

const dockerSocketPath = process.env.DOCKER_SOCKET_PATH || "/var/run/docker.sock";
export const docker = new Docker({ socketPath: dockerSocketPath });
