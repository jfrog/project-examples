#!/bin/bash

cat <<EOF > ~/.pypirc
[distutils]
index-servers = local
[local]
repository: $ARTIFACTORY_PYTHON_REPOSITORY
username: $ARTIFACTORY_USER
password: $ARTIFACTORY_PASS
EOF

echo "Created .pypirc file: Here it is: "
ls -la ~/.pypirc
