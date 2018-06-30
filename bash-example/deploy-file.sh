#!/bin/bash
#https://github.com/JFrogDev/project-examples
usage() {
    echo "Deploy a local file to Artifactory keeping the same file name"
    echo "Usage: $0 localFilePath targetFolder"
    exit 1
}

if [ -z "$2" ]; then
    usage
fi

if [ -z "$ARTIFACTORY_USER" ]; then
    echo "Ensure you have the environment variables set"
    echo "ARTIFACTORY_USER"
    exit 1
fi

if [ -z "$ARTIFACTORY_PASSWORD" ]; then
    echo "Ensure you have the environment variables set"
    echo "ARTIFACTORY_PASSWORD"
    exit 1
fi
localFilePath="$1"
targetFolder="$2"

if [ ! -f "$localFilePath" ]; then
    echo "ERROR: local file $localFilePath does not exists!"
    exit 1
fi


which openssl 2>&1 > /dev/null
if [ $? -ne 0 ]; then
  echo "Cannot find openssl"
  exit 1
fi

echo "Computing checksums"
md5Value="`openssl dgst -md5 "$localFilePath" | cut -d= -f2 |tr -d [:space:]]`"
md5Value="${md5Value:0:32}"
sha1Value="`openssl dgst -sha1 "$localFilePath" | cut -d= -f2 |tr -d [:space:]]`"
sha1Value="${sha1Value:0:40}"
fileName="`basename "$localFilePath"`"

echo $md5Value $sha1Value $localFilePath

echo "INFO: Uploading $localFilePath to $targetFolder/$fileName"
curl -i -X PUT -u $ARTIFACTORY_USER:$ARTIFACTORY_PASSWORD \
 -H "X-Checksum-Md5: $md5Value" \
 -H "X-Checksum-Sha1: $sha1Value" \
 -T "$localFilePath" \
 "$targetFolder/$fileName"
