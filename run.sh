#!/bin/bash

docker build -f Dockerfile . -t mn_dnr_java:latest --no-cache
docker run --name mn_dnr_exporter --rm -v $PWD:/output mn_dnr_java:latest
