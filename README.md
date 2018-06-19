# nc-kotlin-server

## Slides
Accèdez à la présentation [ici](https://docs.google.com/presentation/d/1jvz1lQnQprRyZivuBCx_kBJQDqwwCVNbOooitSpc8gA/edit?usp=sharing)

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

## URL executor
http://ec2-35-180-116-12.eu-west-3.compute.amazonaws.com:3000/
