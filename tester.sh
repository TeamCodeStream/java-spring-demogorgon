#!/bin/bash

sleep 10

echo "Running automated tests..."

while true; do
    curl -s -o /dev/null http://localhost:8081/clm/auto-only
    sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/annotation
    sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/api
    sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/xml
    sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/static
    sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/http
    sleep 1
    # curl -s -o /dev/null http://localhost:8081/clm/facts/blue
    # sleep 1
    # curl -s -o /dev/null http://localhost:8081/clm/facts/green
    # sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/db
    sleep 1
    curl -s -o /dev/null http://localhost:8081/owners?lastName=
    sleep 1
    curl -s -o /dev/null http://localhost:8081/owners/4
    sleep 1
    curl -s -o /dev/null http://localhost:8081/vets.html
    sleep 1
    curl -s -o /dev/null http://localhost:8081/vets
    sleep 1
    # curl -s -o /dev/null http://localhost:8081/oups
    # sleep 1
    curl -s -o /dev/null http://localhost:8081/clm/error
    sleep 1

    timestamp=$(date +"%Y-%m-%dT%H:%M:%S%z")
    echo "$timestamp Completed a full set of operations."

    # go too fast and the agent starts sampling
    sleep 8
done
