# Forum

## Description

This is the backend of a sample forum application.

## Prerequisites

Docker must be installed to run the docker compose, which will run a mariadb container, and the project in another container.

## Building

Run `./gradlew clean build` to build the java file. Then run `docker compose up` to start the mariadb and project.

## Running

Navigate to `localhost:8000` on the browser, and use the `adminUserId` and `adminPassword` as credentials to login. To view the swagger file, navigate to `localhost:8000/swagger-ui/index.html`.