FROM node:6.14.2-jfrog
MAINTAINER eliom@jfrog.com

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY ./package /usr/src/app
COPY www /usr/src/app/bin/www

# Copy npm configurations
COPY npmrc /root/.npmrc

# Install app dependencies
RUN npm install

# Cleanup Artifactory information
RUN rm /root/.npmrc

EXPOSE 3000

CMD [ "npm", "start" ]
