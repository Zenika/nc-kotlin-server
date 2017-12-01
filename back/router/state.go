package router

import (
	"encoding/json"
	"net/http"

	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/Zenika/devfest-code-hero/back/service"
	"github.com/gorilla/mux"
)

func init() {
	router := getRouter()
	router.HandleFunc("/player/{playerID}/state", getState).Methods("GET")
}

func getState(res http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)

	var s model.State
	var err error

	if s, err = service.GetState(vars["playerID"]); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err = json.NewEncoder(res).Encode(s); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
	}
}
