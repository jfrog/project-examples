#
# Description - Example spec file rpm builds. 
#
# Usage - RPMBUILD --define _topdir --define _webappname --define _build_number --define _release_number -ba SPECS/_rpm.spec
#   _webappname - packages the war file to /usr/local/tomcat7/webapps directory
#   _build_number - build number
#   _rpm.spec - rpm specification file
#   _topdir - root directory for the rpmbuild
#   _release_number - webappname release number
#
# Assumes -
# 1. the war file is stored in the rpmbuild/BUILD directory
# 
Summary: Generic RPM packaging  
Name: %{_webappname}
Version: %{_release_number}
Release: %{_build_number}%{?dist}
License: commerical
Group: Applications/Web Applications
Requires: tomcat

URL: https://github.com/JFrogDev/project-examples/rpm-example
Distribution: WSS Linux
Vendor: JFrog, Inc.
Packager: jenkins <soleng-team@jfrog.com>

BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)
BuildArch: noarch

%define tomcat_webapps /usr/share/tomcat/webapps
%define namespace %{_webappname}
%define builddir rpmbuild/BUILD

%description
Builds rpm package from a war file ...

# Prep is used to set up the environment for building the rpm package
# Expansion of source tar balls are done in this section
# Check if the WAR file has been created
%prep
exit 0

# fetch the war file from artifactory
%build
exit 0 

# The installation.
# Copy war file to buildroot's tomcat deployment directory
# Copy config file to buildroot's tomcat config directory
%install
%__rm -rf %{buildroot}
%__mkdir -p %{buildroot}/%{tomcat_webapps}/
%__cp %{_builddir}/%{namespace}.war  %{buildroot}/%{tomcat_webapps}/%{namespace}.war

%clean
exit 0

# Contains a list of the files that are part of the package
# See useful directives such as attr here: http://www.rpm.org/max-rpm-snapshot/s1-rpm-specref-files-list-directives.html
# Set file permissions and ownership
%files
%defattr(-,tomcat,tomcat,-)
%{tomcat_webapps}/%{namespace}.war

# Perform post-installation steps, if needed.
%post
echo "Installation complete."

# Used to store any changes between versions
%changelog
