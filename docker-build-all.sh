#!/bin/bash

find ./ -name "mvnw"  | xargs chmod +x

cd uaa && ./mvnw package -Pprod docker:build &
cd foo && ./mvnw package -Pprod docker:build &
cd bar && ./mvnw package -Pprod docker:build &
cd gateway && ./mvnw package -Pprod docker:build &

wait;

echo "built all!"