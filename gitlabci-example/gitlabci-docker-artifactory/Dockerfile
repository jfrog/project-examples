FROM alpine:3.4

RUN apk update
RUN apk upgrade
RUN apk add openjdk8-jre-base bash curl wget

RUN wget https://releases.jfrog.io/artifactory/artifactory-pro/org/artifactory/pro/jfrog-artifactory-pro/7.18.6/jfrog-artifactory-pro-7.18.6-linux.tar.gz && tar xvzf jfrog-artifactory-pro-7.18.6-linux.tar.gz

RUN rm jfrog-artifactory-pro-7.18.6-linux.tar.gz

EXPOSE 8081

CMD ./artifactory-pro-7.18.6/app/bin/artifactory.sh

