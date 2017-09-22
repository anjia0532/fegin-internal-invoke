#!/bin/bash

find ./ -name "mvnw"  | xargs chmod +x

cd uaa && ./mvnw -s $M2_HOME/conf/settings.xml -Dmaven.test.skip=true package -Pprod docker:build &
cd foo && ./mvnw -s $M2_HOME/conf/settings.xml -Dmaven.test.skip=true package -Pprod docker:build &
cd bar && ./mvnw -s $M2_HOME/conf/settings.xml -Dmaven.test.skip=true package -Pprod docker:build &
cd gateway && ./mvnw -s $M2_HOME/conf/settings.xml -Dmaven.test.skip=true  package -Pprod docker:build &

wait;

echo "built all!"
