package db

import (
	"fmt"

	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/pkg/errors"
)

func stateKey(playerID string) string {
	return fmt.Sprintf("player:%s:state", playerID)
}

// GetInternalState reads a player's state from database
func GetInternalState(playerID string) (model.InternalState, error) {
	var s model.InternalState

	if err := get(stateKey(playerID), &s); err != nil {
		return s, errors.Wrap(err, "Could not get internal state from redis")
	}

	return s, nil
}

// SetInternalState sets a player's state in database
func SetInternalState(playerID string, s model.InternalState) error {
	if err := set(stateKey(playerID), s); err != nil {
		return errors.Wrap(err, "Could not set state in redis")
	}
	return nil
}
