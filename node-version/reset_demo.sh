#!/bin/bash

git pull origin master
sed -i_old 's/FROM.*/FROM node:6.14.2-stretch/' Dockerfile
git commit Dockerfile -m "Reset demo"
git push origin master
