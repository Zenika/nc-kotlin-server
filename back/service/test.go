package service

import (
	"errors"
	"strings"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/executor"
	"github.com/Zenika/devfest-code-hero/back/model"
)

// ExecuteTest executes the tests for a scenario step given some code
func ExecuteTest(playerID string, c model.Code) ([]model.TestResult, error) {
	var player model.Player
	var scenario model.Scenario
	var iState model.InternalState

	var err error

	if player, err = db.GetPlayer(playerID); err != nil {
		return nil, err
	}

	if scenario, err = db.GetScenario(player.Language); err != nil {
		return nil, err
	}

	if iState, err = db.GetInternalState(playerID); err != nil {
		return nil, err
	}

	if iState.Finished {
		return nil, errors.New("Player has already finished")
	}

	step := scenario.Steps[iState.Step]

	r := make([]model.TestResult, 0, len(step.Tests))

	for _, t := range step.Tests {
		var executorOut executor.Out
		if executorOut, err = executor.Execute(player.Language, executor.In{Code: c.Code, Input: t.Input}); err != nil {
			return nil, err
		}

		r = append(r, model.TestResult{
			Ok:  executorOut.ExitCode == 0 && strings.TrimSuffix(executorOut.Out, "\n") == t.Output,
			Out: executorOut.Out,
			Err: executorOut.Err,
		})
	}

	return r, nil
}
