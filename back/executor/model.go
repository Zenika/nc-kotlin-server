package executor

// In contains input parameters of executor
type In struct {
	Code  string `json:"code"`
	Input string `json:"input"`
}

// Out contains output parameters of executor
type Out struct {
	ExitCode int    `json:"exitCode"`
	Out      string `json:"out"`
	Err      string `json:"err"`
}
