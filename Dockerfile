FROM stevenmassaro/scheduled-task-base:3.18 as base
RUN apk add --no-cache --update chromium chromium-chromedriver curl openjdk17-jre-headless

FROM base as test
WORKDIR /app
RUN apk add --no-cache openjdk17-jdk
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN chmod +x mvnw && \
    ./mvnw --batch-mode verify

FROM base as production

COPY target/cuofco-mortgage-checker-1.0-SNAPSHOT-jar-with-dependencies.jar cuofco.jar
ENV COMMAND="java -jar /cuofco.jar && curl -fsS -m 10 --retry 5 -o /dev/null \"\$HEALTHCHECKS_URL\""
