package service

import (
	"errors"
	"strings"
	"time"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/executor"
	"github.com/Zenika/devfest-code-hero/back/model"
)

// Validate validates a step
func Validate(playerID string, c model.Code) (model.ValidateResult, error) {
	var r model.ValidateResult

	var player model.Player
	var scenario model.Scenario
	var iState model.InternalState

	var err error

	if player, err = db.GetPlayer(playerID); err != nil {
		return r, err
	}

	if scenario, err = db.GetScenario(player.Language); err != nil {
		return r, err
	}

	if iState, err = db.GetInternalState(playerID); err != nil {
		return r, err
	}

	if iState.Finished {
		return r, errors.New("Player has already finished")
	}

	step := scenario.Steps[iState.Step]

	success := true
	successCount := 0
	for _, t := range step.Validations {
		var executorOut executor.Out
		if executorOut, err = executor.Execute(player.Language, executor.In{Code: c.Code, Input: t.Input}); err != nil {
			return r, err
		}

		if executorOut.ExitCode == 0 && strings.TrimSuffix(executorOut.Out, "\n") == t.Output {
			successCount++
		} else {
			success = false
		}
	}

	var result model.Result
	switch {
	case success:
		result = step.Results.Success
	case step.Results.PartialSuccess != nil && successCount >= *step.Results.PartialSuccess.Threshold:
		result = *step.Results.PartialSuccess
	default:
		result = step.Results.Failure
	}

	iState.Finished = result.Finish
	iState.Score += result.Score
	if !iState.Finished {
		iState.Step = *result.Step
	}

	if err = db.SetInternalState(playerID, iState); err != nil {
		return r, err
	}

	if iState.Finished {
		pr := model.PlayerResult{
			Player: player,
			Score:  iState.Score,
			Time:   time.Since(player.StartTime).String(),
		}
		if err = db.AddPlayerResult(pr); err != nil {
			return r, err
		}
	}

	return model.ValidateResult{
		Rate:  float32(successCount) / float32(len(step.Validations)),
		Score: result.Score,
		Text:  result.Text,
	}, nil
}
