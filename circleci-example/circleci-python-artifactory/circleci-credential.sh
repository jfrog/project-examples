#!/bin/bash

cat <<EOF > ~/.pypirc
[distutils]
index-servers = local
[local]
repository: https://gcartifactory-us.jfrog.info/artifactory/api/pypi/circleci-pypi
username: $ARTIFACTORY_USERNAME
password: $ARTIFACTORY_PASSWORD
EOF

echo "Created .pypirc file: Here it is: "
ls -la ~/.pypirc