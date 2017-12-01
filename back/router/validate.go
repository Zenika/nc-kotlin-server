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
	router.HandleFunc("/player/{playerID}/validate", validate).Methods("POST")
}

func validate(res http.ResponseWriter, req *http.Request) {
	playerID := mux.Vars(req)["playerID"]
	var c model.Code
	var r model.ValidateResult
	var err error

	if err = json.NewDecoder(req.Body).Decode(&c); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	if r, err = service.Validate(playerID, c); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(res).Encode(r); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}
}
