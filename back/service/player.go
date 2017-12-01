package service

import (
	"errors"
	"strings"
	"time"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/google/uuid"
)

type mailAlreadyPlayed struct{}

func (mailAlreadyPlayed) Error() string {
	return "A game has already been played with this mail"
}

var _ = error(mailAlreadyPlayed{})

// CreatePlayer creates a new player, giving it an id and initializing its state
func CreatePlayer(p model.Player) (model.Player, error) {
	if strings.TrimSpace(p.Name) == "" {
		return p, errors.New("Empty player name")
	}
	if strings.TrimSpace(p.Mail) == "" {
		return p, errors.New("Empty player mail")
	}
	if strings.TrimSpace(p.Language) == "" {
		return p, errors.New("Empty player language")
	}

	// lowerMail := strings.ToLower(p.Mail)
	// var hasMail bool
	// var err error
	// if hasMail, err = db.HasMail(lowerMail); err != nil {
	// 	return p, err
	// }
	// if hasMail {
	// 	return p, mailAlreadyPlayed{}
	// }
	// if err = db.AddMail(lowerMail); err != nil {
	// 	return p, err
	// }

	playerID := uuid.New().String()
	p.PlayerID = playerID
	p.StartTime = time.Now()

	if err := db.SetPlayer(playerID, p); err != nil {
		return p, err
	}

	if err := db.SetInternalState(playerID, model.InternalState{}); err != nil {
		return p, err
	}

	return p, nil
}
