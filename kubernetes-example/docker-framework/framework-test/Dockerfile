FROM docker.artifactory/docker-framework:latest

MAINTAINER Jainish Shah jainishs@jfrog.com

RUN rm -rf /home/exec/tomcat/webapps/*

ADD war/*.war /home/exec/tomcat/webapps/ROOT.war
CMD /bin/bash -c cd /home/exec; /home/exec/tomcat/bin/catalina.sh run
