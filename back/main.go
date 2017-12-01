package main

import (
	"flag"

	"github.com/Zenika/devfest-code-hero/back/db"
	"github.com/Zenika/devfest-code-hero/back/router"
)

func main() {
	flag.Parse()

	if err := db.Connect(); err != nil {
		panic(err)
	}
	defer db.Close()

	if err := router.Start(); err != nil {
		panic(err)
	}
}
