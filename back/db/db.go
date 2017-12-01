package db

import (
	"encoding/json"
	"flag"
	"fmt"

	"github.com/go-redis/redis"
	"github.com/pkg/errors"
)

const (
	defaultRedisHost     = ""
	defaultRedisPort     = 6379
	defaultRedisPassword = ""
)

var (
	redisHost     string
	redisPort     int
	redisPassword string

	client *redis.Client
)

func init() {
	flag.StringVar(&redisHost, "redis.host", defaultRedisHost, "Redis host to connect to")
	flag.IntVar(&redisPort, "redis.port", defaultRedisPort, "Redis port to connect to")
	flag.StringVar(&redisPassword, "redis.password", defaultRedisPassword, "Redis password")
}

// Connect connects to database
func Connect() error {
	client = createRedisClient()

	if err := client.Ping().Err(); err != nil {
		return errors.Wrap(err, "Could not connect to redis")
	}

	return nil
}

// Close closes the connection to the database
func Close() error {
	if err := client.Close(); err != nil {
		return errors.Wrap(err, "Could not close connection to redis")
	}

	return nil
}

func createRedisClient() *redis.Client {
	return redis.NewClient(&redis.Options{
		Addr:     fmt.Sprintf("%s:%d", redisHost, redisPort),
		Password: redisPassword,
	})
}

func get(key string, v interface{}) error {
	var b []byte
	var err error
	if b, err = client.Get(key).Bytes(); err != nil {
		return errors.Wrap(err, "Could not get value from redis")
	}

	if err = json.Unmarshal(b, v); err != nil {
		return errors.Wrap(err, "Could not unmarshal value from JSON")
	}

	return nil
}

func set(key string, v interface{}) error {
	var b []byte
	var err error
	if b, err = json.Marshal(v); err != nil {
		return errors.Wrap(err, "Could not marshal value to JSON")
	}

	if err = client.Set(key, b, 0).Err(); err != nil {
		return errors.Wrap(err, "Could not set value to redis")
	}

	return nil
}

func rpush(key string, v interface{}) error {
	var b []byte
	var err error
	if b, err = json.Marshal(v); err != nil {
		return errors.Wrap(err, "Could not marshal value to JSON")
	}

	if err = client.RPush(key, b).Err(); err != nil {
		return errors.Wrap(err, "Could not rpush value to redis")
	}

	return nil
}

func lrange(key string) ([]string, error) {
	var result []string
	var err error
	if result, err = client.LRange(key, 0, -1).Result(); err != nil {
		return nil, errors.Wrap(err, "Could not lrange value from redis")
	}
	return result, nil
}

func sadd(key string, v interface{}) error {
	if err := client.SAdd(key, v).Err(); err != nil {
		return errors.Wrap(err, "Could not sadd value to redis")
	}
	return nil
}

func sismember(key string, v interface{}) (bool, error) {
	var b bool
	var err error
	if b, err = client.SIsMember(key, v).Result(); err != nil {
		return b, errors.Wrap(err, "Could not sismember value in redis")
	}
	return b, nil
}
