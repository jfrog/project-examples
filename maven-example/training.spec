#
# Example spec file for maven example web app...
#
# Usage - RPMBUILD --define _ghubid --define _build_number -ba SPECS/_ghubid.spec
#
Summary: Simple Maven example web application
Name: stanleyf
Version: 1.0.0
Release: %{_build_number}%{?dist}
License: commerical
Group: Applications/Web Applications
#Requires: apache-tomcat

URL: http://www.jfrog.com
Distribution: WSS Linux
Vendor: JFrog, Inc.
Packager: Stanley Fong <stanleyf@jfrog.com>

BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)
BuildArch: noarch

%define tomcat_webapps /usr/local/tomcat7/webapps
%define namespace %{_ghubid}

%description
It is a web app maven example ...

# Prep is used to set up the environment for building the rpm package
# Expansion of source tar balls are done in this section
# Check if the WAR file has been created
%prep
%__rm -rf ${_builddir}

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
