FROM stevenmassaro/scheduled-task-base:3.18

COPY target/cuofco-mortgage-checker-1.0-SNAPSHOT-jar-with-dependencies.jar cuofco.jar
ENV command="java -jar /cuofco.jar && curl -fsS -m 10 --retry 5 -o /dev/null \"\$HEALTHCHECKS_URL\""

RUN apk add --no-cache --update chromium chromium-chromedriver curl openjdk17-jre-headless
