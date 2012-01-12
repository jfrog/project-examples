#!/bin/sh

scriptDir=`dirname $0`

usage() {
    echo "Promote a buildName/buildNumber to a target repo, after deleting all files on target repo!"
    echo "Usage: $0 buildName buildNumber targetRepo"
    exit 1
}

if [ -z "$3" ]; then
    usage
fi

buildName="$1"
buildNumber="$2"
targetRepo="$3"
artifactoryUser="admin"
artifactoryPassword="password"

jsonFile=/tmp/promote-build-$buildName-$buildNumber.json
sed -e "
 s/@buildName@/$buildName/g;
 s/@buildNumber@/$buildNumber/g;
 s/@targetRepo@/$targetRepo/g;
 s/@artifactoryUser@/$artifactoryUser/g;
 " $scriptDir/promote-build.json > $jsonFile

echo "INFO: Deleting target repo $targetRepo content" && \
curl -X DELETE -u $artifactoryUser:$artifactoryPassword "http://localhost:8081/artifactory/$targetRepo" && \
echo "INFO: Promoting build $buildName:$buildNumber with all dependencies to $targetRepo" && \
curl -X POST -u $artifactoryUser:$artifactoryPassword -H "Content-Type: application/vnd.org.jfrog.artifactory.build.PromotionRequest+json" --data-binary @$jsonFile "http://localhost:8081/artifactory/api/build/promote/$buildName/$buildNumber"
