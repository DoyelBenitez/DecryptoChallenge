#!/bin/bash

#bash docker-compose-build-up.sh dev|docker

if [ -z "$1" ]; then
  echo "Usar: $0 {dev|docker}"
  exit 1
fi

echo "============Ejecutando...============"

param=$1

docker-compose build --no-cache backend

./docker-compose-up.sh $param
