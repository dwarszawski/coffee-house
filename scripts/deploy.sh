#!/bin/sh

cd ../webstore
setsid nohup sbt run &

sleep 10
if lsof -i :8088 ; then
  echo "------------------------------"
  echo "Rest server is available on http://localhost:8088"
  echo "------------------------------"
fi

cd ../webapp
nohup npm start &
sleep 10
if lsof -i :8080 ; then
  echo "------------------------------"
  echo "WebApp is available on http://localhost:8080"
  echo "------------------------------"
fi