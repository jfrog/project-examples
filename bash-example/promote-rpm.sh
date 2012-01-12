#!/bin/sh

usage() {
    echo "Copy the Artifactory folder art-qa/org/artifactory/artifactory-rpm/2.4.x-SNAPSHOT -Containing an RPM- to the rpms-local repository for YUM exposure"
    echo "Usage: $0 srcPath targetPath"
    exit 1
}

if [ -z "$2" ]; then
    usage
fi

srcPath="$1"
targetPath="$2"
artifactoryUser="admin"
artifactoryPassword="password"

if [ "$srcPath" == "dev" ]; then
	srcPath="art-qa/org/artifactory/artifactory-rpm/2.4.x-SNAPSHOT"
else
	srcPath="rpms-local/$srcPath/i586/artifactory"
fi

rpmFolder="rpms-local/$targetPath/i586/artifactory"
echo "INFO: Deleting old rpm content in $rpmFolder" && \
curl -X DELETE -u $artifactoryUser:$artifactoryPassword "http://localhost:8081/artifactory/$rpmFolder" && \
echo "INFO: Promoting RPM from $srcPath to $rpmFolder" && \
curl -X POST -u $artifactoryUser:$artifactoryPassword \
 -H "Accept: application/vnd.org.jfrog.artifactory.storage.CopyOrMoveResult+json" \
 "http://localhost:8081/artifactory/api/copy/$srcPath?to=/$rpmFolder&suppressLayouts=1" && \
echo "INFO: Activating YUM recalculation" && \
curl -X POST -u $artifactoryUser:$artifactoryPassword \
 "http://localhost:8081/artifactory/api/yum/rpms-local?async=0"


