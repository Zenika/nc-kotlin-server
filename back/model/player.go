package model

import "time"

// Player holds information about a player session
type Player struct {
	PlayerID  string    `json:"playerId"`
	Name      string    `json:"name"`
	Mail      string    `json:"mail"`
	Language  string    `json:"language"`
	StartTime time.Time `json:"startTime"`
}

// PlayerResult is the result of a player
type PlayerResult struct {
	Player
	Score int    `json:"score"`
	Time  string `json:"time"`
}
