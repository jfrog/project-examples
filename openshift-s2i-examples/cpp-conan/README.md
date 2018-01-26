# How to containerize C++ applications using Conan and OpenShift S2I #

* git clone
* docker build -t cpp-conan-builder:0.1 .
* s2i build https://github.com/memsharded/example-poco-timer cpp-conan-builder:0.1 timer-app:0.1
* docker run -it timer-app:0.1
