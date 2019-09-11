#!/usr/bin/env bash
mongo admin -u root -p example --eval "db.getSiblingDB('test').createUser({user: 'storm', pwd: 'stormSecret', roles: ['readWrite']})"

