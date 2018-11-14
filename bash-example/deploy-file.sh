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

if command -v sha256sum > /dev/null && command -v sha1sum > /dev/null && command -v md5sum > /dev/null; then 
# If the hashing tools provided by coreutils are installed then use them. They
# give a convenient output that always starts with the hash. Thus, given that
# we know how long each hash will be, we can just cut that bit off the start of
# the string and we have our hash.
    md5Value="$(md5sum "$localFilePath")"
    md5Value="${md5Value:0:32}"
    sha1Value="$(sha1sum "$localFilePath")"
    sha1Value="${sha1Value:0:40}"
    sha256Value="$(sha256sum "$localFilePath")"
    sha256Value="${sha256Value:0:65}"
elif command -v openssl > /dev/null; then
# If coreutils isn't installed then use openssl as a fallback, it's probably
# just as easy to use openssl all the time as it's more likely to be installed
# but the weird output syntax makes it less pleasant to strip the "non-hash"
# value out for use.
    md5Value="$(openssl dgst -md5 "$localFilePath")"
    md5Value="${md5Value##*)= }"
    sha1Value="$(openssl dgst -sha1 "$localFilePath")"
    sha1Value="${sha1Value##*)= }"
    sha256Value="$(openssl dgst -sha256 "$localFilePath")"
    sha256Value="${sha256Value##*)= }"
else
# There are a bunch of other ways to get the hash but it's probably just as
# simple to fail here and if you are on a system with some weird set of
# packages installed you can just customise this script.
  echo "ERROR: install coreutils or openssl in order to find the md5, sha1 and \
sha256 hashes of your file."
  exit 1
fi

fileName="${localFilePath##*/}"

echo "INFO: File: $localFilePath"
echo "INFO: MD5: $md5Value"
echo "INFO: SHA1: $sha1Value"
echo "INFO: SHA256 Hash: $sha256Value"

echo "INFO: Uploading $localFilePath to $targetFolder/$fileName"
curl -i -X PUT -u "$artifactoryUser:$artifactoryPassword" \
 -H "X-Checksum-Md5: $md5Value" \
 -H "X-Checksum-Sha1: $sha1Value" \
 -H "X-Checksum-Sha256: $sha256Value" \
 -T "$localFilePath" \
 "$targetFolder/$fileName"

