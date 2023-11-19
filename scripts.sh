#!/bin/bash

# MVC test
echo 'GET http://localhost:8080/mvc/users/1?delay=2000' | vegeta attack -duration=10s -rate=300/s | vegeta report

# WebFlux test
echo 'GET http://localhost:8081/webflux/users/1?delay=2000' | vegeta attack -duration=10s -rate=300/s | vegeta report

# coroutine
curl "http://localhost:8081/coroutine/users/1"

# flow
curl "http://localhost:8081/coroutine/users?delay=1000"
echo 'GET http://localhost:8080/mvc/users/1?delay=2000' | vegeta attack -duration=10s -rate=100/s | vegeta report

echo 'GET http://localhost:8081/webflux/users/1?delay=2000' | vegeta attack -duration=10s -rate=100/s | vegeta report
