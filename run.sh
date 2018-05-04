#!/bin/bash

mvn clean package
LD_LIBRARY_PATH="libs/" java -jar target/samplegs-*-jar-with-dependencies.jar