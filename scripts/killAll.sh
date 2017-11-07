#!/bin/sh

if lsof -i :8088 ; then
   kill $(lsof -i :8088 | awk '{print $2}' | tail -1)
fi

if lsof -i :8080 ; then
   kill $(lsof -i :8080 | grep node | tail -1 | awk '{print $2}')
fi