#!/bin/bash
# this script sets up Jenkins job to create rpm per name - on entry @rpm-exmaple directory
# Inputs
# $1 = Build Number that is passed by Jenkins
# $2 = Optional - webapp name to use for tomcat7/webapps directory


BUILD_DEFINE="_build_number $1"
RPMDIR_DEFINE="_topdir $(pwd)/rpmbuild"
webappname=${2:-""}
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
    #extract the revision number and module name if not specified 
    warname=$(basename $warfile)
    rev="$(echo $warname | cut -d'-' -f2)"
    RELEASE_DEFINE="_release_number $rev"
    
    if [ -z "$webappname" ]; then
	webappname="$(echo $warname | cut -d'-' -f1)"
    fi
    WEBAPP_DEFINE="_webappname $webappname"
    mv $warfile rpmbuild/BUILD/$webappname.war
    break
done
cp $workingDir/rpm.spec rpmbuild/SPECS/.

cd rpmbuild
rpmbuild --define "$RPMDIR_DEFINE" --define "$WEBAPP_DEFINE" --define "$BUILD_DEFINE" --define "$RELEASE_DEFINE" -ba SPECS/rpm.spec
