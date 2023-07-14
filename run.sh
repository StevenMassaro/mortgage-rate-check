#!/bin/sh
echo "$(date) - start scheduler"
while :; do
    echo "$(date) - execute rate check"

    java -jar /cuofco.jar && curl -fsS -m 10 --retry 5 -o /dev/null "$HEALTHCHECKS_URL"

    echo "$(date) - sleep for 1 day"
    sleep 1d
done
