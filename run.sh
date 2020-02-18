#!/bin/bash

[[ ! $1 ]] && echo Please provide the argument "micro" or "hybrid" && exit

mvn clean test -P "$1"
#cd .. && sh database-populate.sh
