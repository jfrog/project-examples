# How to containerize C++ applications using Conan and OpenShift S2I #

## Clone project ##
``` git clone https://github.com/JFrog/project-examples.git && cd project-examples/openshift-s2i-examples/cpp-conan ```

## Install source-to-image / s2i / sti ##
Follow the [instructions](https://github.com/openshift/source-to-image#installation) to install s2i

## Generate the builder image that includes conan, make, gcc 7.x ##
``` docker build -t cpp-conan-builder:0.1 . ```

* The builder image uses [conan](http://docs.conan.io/en/latest/introduction.html) to modularize a C++ application.


## Containerize C++ application using s2i ##
```s2i build https://github.com/memsharded/example-poco-timer cpp-conan-builder:0.1 timer-app:0.1```

* C++ dependencies including the transitive dependencies are pulled from conan-center. 
* C++ packages can also be pulled from Artifactory since conan repositories are supported.

Access tokens can be used to authenticate with Artifactory $RT_URL and access conan repositories


``` 

export RESPONSE=$(curl -H "X-JFrog-Art-Api:$RT_API_KEY" -XPOST "$RT_URL/api/security/token" -d "username=$RT_USER" -d "scope=member-of-groups:readers" -d "expires_in=600")

export RT_ACCESS_TOKEN=$(echo "$RESPONSE"| jq -r .access_token) 

s2i build https://github.com/memsharded/example-poco-timer cpp-conan-builder:0.1 timer-app:0.1 -e "RT_CONAN_URL=http://art-url/artifactory/api/conan/conan-local" -e "RT_USER=$RT_USER" -e "RT_ACCESS_TOKEN=$RT_ACCESS_TOKEN" 

```

## Run your C++ application ##
```docker run -it timer-app:0.1```


