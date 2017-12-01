package model

// Scenario holds all the information of a scenario
type Scenario struct {
	Language  string `json:"language"`
	MapImg    string `json:"mapImg"`
	AvatarImg string `json:"avatarImg"`
	Steps     []Step `json:"steps"`
}

// Step holds the information of one step of a scenario
type Step struct {
	Title       string `json:"title"`
	Text        string `json:"text"`
	MapPosition struct {
		X int `json:"x"`
		Y int `json:"y"`
	} `json:"mapPosition"`
	Template    string `json:"template"`
	Tests       []Test `json:"tests"`
	Validations []Test `json:"validations"`
	Results     struct {
		Success        Result  `json:"success"`
		PartialSuccess *Result `json:"partialSuccess,omitempty"`
		Failure        Result  `json:"failure"`
	} `json:"results"`
}

// Result defines an expected result for a step of a scenario
type Result struct {
	Finish    bool   `json:"finish"`
	Step      *int   `json:"step"`
	Score     int    `json:"score"`
	Threshold *int   `json:"threshold,omitempty"`
	Text      string `json:"text,omitempty"`
}
