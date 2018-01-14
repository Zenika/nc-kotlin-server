#!/bin/sh
for language in {js,python,php}; do
  curl -XPOST -H"Content-Type: application/json" --data @${language}.json http://localhost:8080/scenario
done
