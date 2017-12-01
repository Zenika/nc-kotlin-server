package executor

import (
	"bytes"
	"encoding/json"
	"flag"
	"fmt"
	"net/http"

	"github.com/pkg/errors"
)

const (
	defaultExecutorURL = "http://localhost:3000"
)

var (
	executorURL string
)

func init() {
	flag.StringVar(&executorURL, "executor.URL", defaultExecutorURL, "Executor URL to connect to")
}

// Execute calls the executor
func Execute(language string, in In) (Out, error) {
	var out Out

	var b bytes.Buffer
	var err error
	if err = json.NewEncoder(&b).Encode(in); err != nil {
		return out, errors.Wrap(err, "Could not encode executor input to JSON")
	}

	var res *http.Response
	if res, err = http.Post(fmt.Sprintf("%s/language/%s", executorURL, language), "application/json", &b); err != nil {
		return out, errors.Wrap(err, "Error while calling executor")
	}

	if err = json.NewDecoder(res.Body).Decode(&out); err != nil {
		return out, errors.Wrap(err, "Could not decode executor output from JSON")
	}

	return out, nil
}

// GetLanguages gets the executor supported languages
func GetLanguages() ([]string, error) {
	var res *http.Response
	var err error
	if res, err = http.Get(fmt.Sprintf("%s/language", executorURL)); err != nil {
		return nil, errors.Wrap(err, "Error while calling executor")
	}

	var out []struct {
		Key string `json:"key"`
	}
	if err = json.NewDecoder(res.Body).Decode(&out); err != nil {
		return nil, errors.Wrap(err, "Could not decode executor output from JSON")
	}

	languages := make([]string, 0, len(out))
	for _, l := range out {
		languages = append(languages, l.Key)
	}

	return languages, nil
}
