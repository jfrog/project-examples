FROM alpine:3.4

RUN apk update
RUN apk upgrade
RUN apk add openjdk8-jre-base bash curl zip wget

RUN wget https://dl.bintray.com/jfrog/artifactory-pro/org/artifactory/pro/jfrog-artifactory-pro/5.11.0/jfrog-artifactory-pro-5.11.0.zip && unzip jfrog-artifactory-pro-5.11.0.zip

RUN rm jfrog-artifactory-pro-5.11.0.zip

EXPOSE 8081

CMD ./artifactory-pro-5.11.0/bin/artifactory.sh

