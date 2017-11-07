#!/bin/sh

cd ../webstore
sbt update
sbt compile

cd ../webapp
npm install
npm build ./