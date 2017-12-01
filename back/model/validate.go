package model

// ValidateResult is the result of the valiation of a step
type ValidateResult struct {
	Rate  float32 `json:"rate"`
	Score int     `json:"score"`
	Text  string  `json:"text,omitempty"`
}
