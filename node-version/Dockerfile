FROM node:6

# Create app directory
RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

# Bundle app source
COPY ./package /usr/src/app

# Copy npm configurations
COPY npmrc /root/.npmrc

# Install app dependencies
RUN npm install

# Cleanup Artifactory information
RUN rm /root/.npmrc

EXPOSE 3000

CMD [ "npm", "start" ]
