package service

import (
	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/executor"
	"github.com/Zenika/devfest-code-hero/back/model"
)

// GetLanguages lists the available languages
func GetLanguages() ([]model.Language, error) {
	var keys []string
	var err error

	if keys, err = executor.GetLanguages(); err != nil {
		return nil, err
	}

	languages := make([]model.Language, 0, len(keys))
	for _, key := range keys {
		var s model.Scenario
		if s, err = db.GetScenario(key); err != nil {
			continue
		}

		languages = append(languages, model.Language{
			Key:       key,
			AvatarImg: s.AvatarImg,
		})
	}

	return languages, nil
}
