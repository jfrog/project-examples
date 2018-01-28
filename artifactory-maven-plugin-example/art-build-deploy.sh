#!/bin/bash

usage() {
    echo "Deploy a maven-based build to Artifactory using Artifactory maven plugin"
    echo "Usage: $0 buildnumber"
    exit 1
}

if [ -z "$1" ]; then
    usage
fi

buildnumber="$1"
mavenSettingsFile="settings.xml"

if [ ! -f "$mavenSettingsFile" ]; then
    echo "ERROR: file $mavenSettingsFile does not exist!"
    exit 1
fi

mvn deploy -Dbuildnumber="$buildnumber" -s $mavenSettingsFile
