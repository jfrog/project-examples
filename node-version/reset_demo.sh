#!/bin/bash

git pull origin master
sed -i_old 's/FROM.*/FROM node:6/' Dockerfile
git commit Dockerfile -m "Reset demo"
git push origin master
