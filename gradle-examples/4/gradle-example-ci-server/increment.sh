if sed --version
then sed -r 's/(currentVersion)=([0-9]+).([0-9]+).([0-9]+)/echo "\1=\2.\3.$BUILD_NUMBER"/e' gradle.properties -i
else gsed -r 's/(currentVersion)=([0-9]+).([0-9]+).([0-9]+)/echo "\1=\2.\3.$BUILD_NUMBER"/e' gradle.properties -i
fi
