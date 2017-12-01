# Backend

## Install
```sh
go get github.com/golang/dep
dep ensure
```

## Start
```sh
docker-compose up -d
go run main.go
```

## Usage
```
-executor.URL string
    Executor URL to connect to (default "http://localhost:3000")
-http.host string
    HTTP host to listen on
-http.port int
    HTTP port to listen on (default 8080)
-redis.host string
    Redis host to connect to
-redis.password string
    Redis password
-redis.port int
    Redis port to connect to (default 6379)
```

## Connect to redis
```sh
docker-compose exec redis redis-cli
```

## Endpoints
- GET /player/{playerID}
- POST /player
- GET /scenario/{language}
- PUT /scenario/{language}
- GET /player/{playerID}/state
- POST /player/{playerID}/test
- POST /player/{playerID}/validate
- GET /language
