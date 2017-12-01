package model

// State holds public information about the state of a player
type State struct {
	MapImg      string `json:"mapImg"`
	AvatarImg   string `json:"avatarImg"`
	Finished    bool   `json:"finished"`
	Title       string `json:"title"`
	Text        string `json:"text"`
	MapPosition struct {
		X int `json:"x"`
		Y int `json:"y"`
	} `json:"mapPosition"`
	Template string `json:"template"`
	Tests    []Test `json:"tests"`
	Score    int    `json:"score"`
}

// InternalState holds private information about the state of a player
type InternalState struct {
	Finished bool `json:"finished"`
	Step     int  `json:"step"`
	Score    int  `json:"score"`
}
