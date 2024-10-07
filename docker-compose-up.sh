#!/bin/bash

#bash docker-compose-up.sh dev|docker

if [ -z "$1" ]; then
  echo "Usar: $0 {dev|docker}"
  exit 1
fi

echo "============Ejecutando docker-compose-up.sh============"

param=$1

case "$param" in
  dev)
    echo "Ejecutando docker-compose --env-file src/main/resources/credentials/credential-dev.env up"
    docker-compose --env-file src/main/resources/credentials/credential-dev.env up
    ;;
  docker)
    echo "Ejecutando docker-compose --env-file src/main/resources/credentials/credential-docker.env up"
    docker-compose --env-file src/main/resources/credentials/credential-docker.env up
    ;;
  *)
    echo "Prametros invalido. Usar {dev|docker}"
    exit 1
    ;;
esac