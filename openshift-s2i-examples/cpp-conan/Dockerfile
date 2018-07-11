FROM centos/devtoolset-7-toolchain-centos7 

MAINTAINER Ankush Chadha <ankushc@jfrog.com>

USER 0

RUN INSTALL_PKGS="git make cmake epel-release" && \
	yum install -y --setopt=tsflags=nodocs install $INSTALL_PKGS && \
	rpm -V $INSTALL_PKGS && \
	yum clean all

RUN yum install -y python-pip && pip install conan

ENV GIT_COMMITTER_EMAIL=ankushc@jfrog.com
ENV GIT_COMMITTER_NAME=AnkushC

COPY ./.s2i/bin/ /usr/local/s2i

LABEL io.openshift.s2i.scripts-url="image:///usr/local/s2i"
RUN mkdir -p /opt/app-root && chown -R 1001:0 /opt/app-root

ENV HOME=/opt/app-root
ENV RT_CONAN_URL=
ENV RT_ACCESS_TOKEN=
ENV RT_USER=

USER 1001
WORKDIR ${HOME}
CMD ["echo","S2I Builder image to build C++ application via Conan"]

 
