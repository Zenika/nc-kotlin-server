package router

import (
	"encoding/json"
	"net/http"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/Zenika/devfest-code-hero/back/service"
	"github.com/gorilla/mux"
)

func init() {
	router := getRouter()
	router.HandleFunc("/player", createPlayer).Methods("POST")
	router.HandleFunc("/player/{playerID}", getPlayer).Methods("GET")
}

func createPlayer(res http.ResponseWriter, req *http.Request) {
	var p model.Player
	var err error

	if err = json.NewDecoder(req.Body).Decode(&p); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	if p, err = service.CreatePlayer(p); err != nil {
		sendHTTPError(res, http.StatusBadRequest, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err := json.NewEncoder(res).Encode(p); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}
}

func getPlayer(res http.ResponseWriter, req *http.Request) {
	playerID := mux.Vars(req)["playerID"]

	var p model.Player
	var err error

	if p, err = db.GetPlayer(playerID); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err = json.NewEncoder(res).Encode(p); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
	}
}
