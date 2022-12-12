#!/bin/bash
./mvnw package
cp target/*SNAPSHOT.jar .
docker-compose up