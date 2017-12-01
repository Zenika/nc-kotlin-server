package router

import (
	"encoding/json"
	"net/http"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/gorilla/mux"
)

func init() {
	router := getRouter()
	router.HandleFunc("/scenario/{language}", setScenario).Methods("PUT")
	router.HandleFunc("/scenario/{language}", getScenario).Methods("GET")
}

func setScenario(res http.ResponseWriter, req *http.Request) {
	language := mux.Vars(req)["language"]

	var s model.Scenario
	if err := json.NewDecoder(req.Body).Decode(&s); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	if err := db.SetScenario(language, s); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(res).Encode(s); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}
}

func getScenario(res http.ResponseWriter, req *http.Request) {
	language := mux.Vars(req)["language"]

	var s model.Scenario
	var err error

	if s, err = db.GetScenario(language); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err = json.NewEncoder(res).Encode(s); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
	}
}
