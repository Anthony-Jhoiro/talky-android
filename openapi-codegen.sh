#!/bin/bash

DOCKER_EXECUTABLE=$(which docker)
OPENAPI_URLS=("https://api.talky.jho.ovh/v3/api-docs/posts" "https://api.talky.jho.ovh/v3/api-docs/users" "https://api.talky.jho.ovh/v3/api-docs/social")

if [ -z "$DOCKER_EXECUTABLE" ]; then
  echo "Docker executable not found on your system when running which docker"
  exit 1
fi


OPENAPIGEN_DOCKER_IMAGE=openapitools/openapi-generator-cli:latest

function codegen() {
    local url="$1"
    local apiName=$(basename "$url")

    $DOCKER_EXECUTABLE run --rm -v "$PWD/todelete:/local/app" --name openapi-codegen $OPENAPIGEN_DOCKER_IMAGE \
      generate \
      -g kotlin \
      --library jvm-retrofit2 \
      -i "$url" \
      -o /local/app \
      -p sourceFolder=/src/main/java
      --package-name "com.talky.mobile.api.$apiName"
}

if (( $# == 0 )); then
  # Generate code using docker
  for uri in ${OPENAPI_URLS[@]}; do
    codegen "$uri"
  done

else
  # If arguments are passed to the script pass them to the docker cmd
  $DOCKER_EXECUTABLE run --rm -v "$PWD/codegen-config:/local" --name openapi-codegen $OPENAPIGEN_DOCKER_IMAGE $@
fi









