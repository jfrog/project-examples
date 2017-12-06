FROM alpine:3.4

RUN apk update
RUN apk upgrade
RUN apk add openjdk8-jre-base bash curl zip wget

RUN wget https://dl.bintray.com/jfrog/artifactory-pro/org/artifactory/pro/jfrog-artifactory-pro/5.4.6/jfrog-artifactory-pro-5.4.6.zip && unzip jfrog-artifactory-pro-5.4.6.zip

RUN rm jfrog-artifactory-pro-5.4.6.zip

EXPOSE 8081

CMD ./artifactory-pro-5.4.6/bin/artifactory.sh


