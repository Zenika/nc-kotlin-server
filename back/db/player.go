package db

import (
	"encoding/json"
	"fmt"

	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/pkg/errors"
)

func playerKey(playerID string) string {
	return fmt.Sprintf("player:%s", playerID)
}

// GetPlayer reads a player from database
func GetPlayer(playerID string) (model.Player, error) {
	var p model.Player

	if err := get(playerKey(playerID), &p); err != nil {
		return p, errors.Wrap(err, "Could not get player from redis")
	}

	return p, nil
}

// SetPlayer sets a player in database
func SetPlayer(playerID string, p model.Player) error {
	if err := set(playerKey(playerID), p); err != nil {
		return errors.Wrap(err, "Could not set player in redis")
	}

	return nil
}

// AddPlayerResult adds a player result in database
func AddPlayerResult(pr model.PlayerResult) error {
	if err := rpush("playerResult", pr); err != nil {
		return errors.Wrap(err, "Could not rpush player result in redis")
	}

	return nil
}

// GetPlayerResults get players results from database
func GetPlayerResults() ([]model.PlayerResult, error) {
	var sResults []string
	var err error
	if sResults, err = lrange("playerResult"); err != nil {
		return nil, errors.Wrap(err, "Could not lrange player results from redis")
	}

	results := make([]model.PlayerResult, 0, len(sResults))
	for _, sResult := range sResults {
		result := model.PlayerResult{}
		if err = json.Unmarshal([]byte(sResult), &result); err != nil {
			return nil, errors.Wrap(err, "Could not unmarshal player result from JSON")
		}
		results = append(results, result)
	}

	return results, nil
}
