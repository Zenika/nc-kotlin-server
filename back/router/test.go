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
	router.HandleFunc("/player/{playerID}/test", executeTest).Methods("POST")
}

func executeTest(res http.ResponseWriter, req *http.Request) {
	playerID := mux.Vars(req)["playerID"]
	var c model.Code
	var r []model.TestResult
	var err error

	if err = json.NewDecoder(req.Body).Decode(&c); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	if r, err = service.ExecuteTest(playerID, c); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(res).Encode(r); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}
}
