#!/bin/sh
curl -XPUT http://localhost:8080/scenario/js --data @js.json
curl -XPUT http://localhost:8080/scenario/python --data @python.json
curl -XPUT http://localhost:8080/scenario/php --data @php.json
