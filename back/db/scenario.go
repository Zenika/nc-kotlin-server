package db

import (
	"fmt"

	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/pkg/errors"
)

func scenarioKey(language string) string {
	return fmt.Sprintf("scenario:%s", language)
}

// GetScenario reads a scenario from database
func GetScenario(language string) (model.Scenario, error) {
	var s model.Scenario

	if err := get(scenarioKey(language), &s); err != nil {
		return s, errors.Wrap(err, "Could not get scenario from redis")
	}

	return s, nil
}

// SetScenario sets a scenario in database
func SetScenario(language string, s model.Scenario) error {
	s.Language = language
	if err := set(scenarioKey(language), s); err != nil {
		return errors.Wrap(err, "Could not set scenario in redis")
	}
	return nil
}
