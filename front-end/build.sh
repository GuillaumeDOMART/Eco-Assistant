#!/bin/bash
./mvnw package
cp target/front-end-0.0.1-SNAPSHOT.jar .
docker-compose up