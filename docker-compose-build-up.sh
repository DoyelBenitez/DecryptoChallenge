#!/bin/bash

#bash docker-compose-build-up.sh dev|docker

if [ -z "$1" ]; then
  echo "Usar: $0 {dev|docker}"
  exit 1
fi

echo "============Ejecutando...============"
set -e

docker-compose build --no-cache backend

param=$1
./docker-compose-up.sh $param
