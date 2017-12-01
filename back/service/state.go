package service

import (
	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/model"
)

// GetState gives the current state of a player
func GetState(playerID string) (model.State, error) {
	var player model.Player
	var scenario model.Scenario
	var iState model.InternalState

	var state model.State
	var err error

	if player, err = db.GetPlayer(playerID); err != nil {
		return state, err
	}

	if scenario, err = db.GetScenario(player.Language); err != nil {
		return state, err
	}

	if iState, err = db.GetInternalState(playerID); err != nil {
		return state, err
	}

	step := scenario.Steps[iState.Step]

	state.MapImg = scenario.MapImg
	state.AvatarImg = scenario.AvatarImg
	state.Title = step.Title
	state.Text = step.Text
	state.Template = step.Template
	state.Tests = step.Tests
	state.Finished = iState.Finished
	state.Score = iState.Score

	return state, nil
}
