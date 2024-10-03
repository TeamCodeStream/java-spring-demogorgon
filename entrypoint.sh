#!/usr/bin/env bash

[ -z "$NEW_RELIC_LICENSE_KEY" ] && echo "Need to set NEW_RELIC_LICENSE_KEY" && exit 1;

APP_PID=
TESTER_PID=

cleanup() {
  echo "Killing app pid $APP_PID"
  kill "$APP_PID"
  wait "$APP_PID"
  echo "Killing tester pid $TESTER_PID"
  kill "$TESTER_PID"
}

trap cleanup INT TERM

# start app
nohup java -Xmx3g -Xms3g -javaagent:/app/newrelic/newrelic.jar -jar java-spring-demogorgon-1.0.0.jar &
APP_PID=$!

# generate load
./tester.sh &
TESTER_PID=$!

echo "Running with APP_PID $APP_PID, TESTER_PID, $TESTER_PID"

wait $TESTER_PID
