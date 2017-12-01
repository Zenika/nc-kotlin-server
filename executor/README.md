## Install
```sh
npm i
```

## Start
```sh
npm start
```

## Environment variables
 - `DOCKER_SOCKET_PATH` : Path du socket docker, défaut : `/var/run/docker.sock`
 - `CONFIG_PATH` : Path du fichier de config, défaut : `<working_dir>/config.json`
 - `TMPDIR` : Dossier temporaire, défaut : `<working_dir>/tmp/`

## Configuration
```json
{
  "languages": [ // Liste des langages
    {
      "key": "js", // Clé du langage
      "extension": ".js", // Extension de fichier
      "imageName": "node", // Nom d'image docker
      "imageTag": "8.2-alpine", // Tag d'image docker
      "runCmd": "/bin/ash", // Commande de démarrage du container
      "execCmd": ["timeout", "-t", "2", "node", "--harmony"], // Commande d'exécution de code
      "tempDir": "/tmp/scripts", // Dossier temporaire à l'intérieur du container, défaut /tmp
      "volumes": ["/tmp"] // Liste de volumes pour le container
    }
  ]
}
```

## Endpoints
 - Lister les langages : GET http://localhost:3000/language
 - Exécuter du code : POST http://localhost:3000/language/:language avec payload :
```json
  {
    "code": "console.log('Hello World !')",
    "input": "..."
  }
```
