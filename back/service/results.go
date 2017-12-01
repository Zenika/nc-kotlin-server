package service

import (
	"sort"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/model"
)

type byScoreAndTime []model.PlayerResult

func (results byScoreAndTime) Len() int {
	return len(results)
}

func (results byScoreAndTime) Less(i, j int) bool {
	a, b := results[i], results[j]
	if a.Score != b.Score {
		return a.Score > b.Score
	}
	return a.Time < b.Time
}

func (results byScoreAndTime) Swap(i, j int) {
	results[i], results[j] = results[j], results[i]
}

// GetPlayerResults gets all player results sorted by score descending and time ascending
func GetPlayerResults() ([]model.PlayerResult, error) {
	var results []model.PlayerResult
	var err error
	if results, err = db.GetPlayerResults(); err != nil {
		return nil, err
	}

	sort.Sort(byScoreAndTime(results))

	return results, nil
}
