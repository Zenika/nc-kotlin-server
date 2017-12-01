package model

// Test defines a test/validation for a step of a scenario
type Test struct {
	Title  string `json:"title"`
	Input  string `json:"input"`
	Output string `json:"output"`
}

// TestResult is the result of a test/validation
type TestResult struct {
	Ok  bool   `json:"ok"`
	Out string `json:"out"`
	Err string `json:"err"`
}
