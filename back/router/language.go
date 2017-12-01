package router

import (
	"encoding/json"
	"net/http"

	"github.com/Zenika/devfest-code-hero/back/model"
	"github.com/Zenika/devfest-code-hero/back/service"
)

func init() {
	router := getRouter()
	router.HandleFunc("/language", getLanguages).Methods("GET")
}

func getLanguages(res http.ResponseWriter, req *http.Request) {
	var languages []model.Language
	var err error

	if languages, err = service.GetLanguages(); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
		return
	}

	res.Header().Set("Content-Type", "application/json")
	if err = json.NewEncoder(res).Encode(&languages); err != nil {
		sendHTTPError(res, http.StatusInternalServerError, err)
	}
}
