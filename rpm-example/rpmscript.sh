#!/bin/bash
# this script sets up Jenkins job to create rpm per name - on entry @rpm-exmaple directory
# Inputs
# $1 = webapp name to use for tomcat7/webapps directory
# $2 = Build Number that is passed by Jenkins

WEBAPP_DEFINE="_webappname $1"
BUILD_DEFINE="_build_number $2"
RPMDIR_DEFINE="_topdir $(pwd)/rpmbuild
workingDir=$(pwd)

# Setup rpmbuild environment
mkdir -p rpmbuild
cd rpmbuild
for dir in BUILD RPMS SOURCES SPECS SRPMS
do
 [[ -d $dir ]] && rm -Rf $dir
  mkdir $dir
done
cd $workingDir

# Put the training spec in the SPEC folder and source in Build folder
for warfile in `find SOURCE -name '*.war'` 
do
    #extract the revision number
    warname=$(basename $warfile)
    rev="$(echo $warname | cut -d'-' -f2)"
    RELEASE_DEFINE="_release_number $rev"

    mv $warfile rpmbuild/BUILD/$1.war
    break
done
cp $workingDir/rpm.spec rpmbuild/SPECS/.

cd rpmbuild
rpmbuild --define "$RPMDIR_DEFINE" --define "$WEBAPP_DEFINE" --define "$BUILD_DEFINE" --define "$RELEASE_DEFINE" -ba SPECS/rpm.spec
