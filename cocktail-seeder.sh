#!/usr/bin/env bash

if [ -z "$1" ];then
    echo "Please provide seed endpoint as input argument"
    exit
fi

for row in $(cat repository.json | jq -r '.[] | @base64'); do
    _jq() {
        echo ${row} | base64 --decode | jq -r ${1}
    }
    curl -X POST "$1" -d "$(_jq)" | jq .
done
