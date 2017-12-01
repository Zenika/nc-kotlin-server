package router

import (
	"encoding/json"
	"flag"
	"fmt"
	"net/http"
	"sync"

	"github.com/gorilla/handlers"
	"github.com/gorilla/mux"
	"github.com/pkg/errors"
)

type httpError struct {
	Error string `json:"error"`
}

const (
	defaultHTTPHost = ""
	defaultHTTPPort = 8080
)

var (
	httpHost string
	httpPort int

	router     *mux.Router
	initRouter sync.Once
)

func init() {
	flag.StringVar(&httpHost, "http.host", defaultHTTPHost, "HTTP host to listen on")
	flag.IntVar(&httpPort, "http.port", defaultHTTPPort, "HTTP port to listen on")
}

func getRouter() *mux.Router {
	initRouter.Do(func() {
		router = mux.NewRouter()
	})
	return router
}

// Start starts the router
func Start() error {
	if err := http.ListenAndServe(fmt.Sprintf("%s:%d", httpHost, httpPort), handlers.CORS()(getRouter())); err != nil {
		return errors.Wrap(err, "Could not start HTTP server")
	}
	return nil
}

func sendHTTPError(res http.ResponseWriter, status int, pErr error) {
	res.WriteHeader(status)
	if err := json.NewEncoder(res).Encode(&httpError{pErr.Error()}); err != nil {
		panic(err)
	}
}
