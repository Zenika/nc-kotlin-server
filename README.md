# nc-kotlin-server

## Installation
Start Redis database and frontend with :
```sh
docker-compose up -d
```

## Run
Start backend :
```sh
cd server
gradle bootRun
# Or specifying executor's URL
gradle bootRun -Dexecutor.url=http://localhost:3000
```
