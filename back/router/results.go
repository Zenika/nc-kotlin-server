package router

import (
	"encoding/json"
	"net/http"

	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/Zenika/devfest-code-hero/back/service"
)

func init() {
	router := getRouter()
	router.HandleFunc("/results", getResults).Methods("GET")
}

func getResults(res http.ResponseWriter, req *http.Request) {
	var r []model.PlayerResult
	var err error

	if r, err = service.GetPlayerResults(); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err = json.NewEncoder(res).Encode(r); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
	}
}
