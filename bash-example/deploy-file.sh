#!/bin/bash

usage() {
    echo "Deploy a local file to Artifactory keeping the same file name"
    echo "Usage: $0 localFilePath targetFolder"
    exit 1
}

if [ -z "$2" ]; then
    usage
fi

localFilePath="$1"
targetFolder="$2"
artifactoryUser="admin"
artifactoryPassword="password"

if [ ! -f "$localFilePath" ]; then
    echo "ERROR: local file $localFilePath does not exists!"
    exit 1
fi

which md5sum || exit $?
which sha1sum || exit $?

md5Value="`md5sum "$localFilePath"`"
md5Value="${md5Value:0:32}"
sha1Value="`sha1sum "$localFilePath"`"
sha1Value="${sha1Value:0:40}"
fileName="`basename "$localFilePath"`"

echo $md5Value $sha1Value $localFilePath

echo "INFO: Uploading $localFilePath to $targetFolder/$fileName"
curl -i -X PUT -u $artifactoryUser:$artifactoryPassword \
 -H "X-Checksum-Md5: $md5Value" \
 -H "X-Checksum-Sha1: $sha1Value" \
 -T "$localFilePath" \
 "$targetFolder/$fileName"

