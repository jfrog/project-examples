# Artifactory Integration with GitLab CI using Maven Artifactory Plugin

## Store build information and build artifacts to JFrog Artifactory

### Steps to Integrate GitLab CI with Artifactory.

#### Step 1:

Configure the following Secret Variables in CI/CD Settings of your Maven project: ARTIFACTORY_URL, ARTIFACTORY_USER, ARTIFACTORY_PASS, MAVEN_REPO_KEY.

![screenshot](img/Screen_Shot1.png)

#### Step 2:

Configure artifactory-maven-plugin to your project by copying following to your pom.xml:
```
            <plugins>
              <plugin>
                <groupId>org.jfrog.buildinfo</groupId>
                <artifactId>artifactory-maven-plugin</artifactId>
                <version>2.6.1</version>
                <inherited>false</inherited>
                <executions>
                  <execution>
                  <id>build-info</id>
                  <goals>
                    <goal>publish</goal>
                  </goals>
                  <configuration>
                    <deployProperties>
                      <gradle>awesome</gradle>
                      <review.team>qa</review.team>
                    </deployProperties>
                    <publisher>
                      <contextUrl>${env.ARTIFACTORY_URL}</contextUrl>
                      <username>${env.ARTIFACTORY_USER}</username>
                      <password>${env.ARTIFACTORY_PASS}</password>
                      <repoKey>${env.MAVEN_REPO_KEY}</repoKey>
                      <snapshotRepoKey>${env.MAVEN_REPO_KEY}</snapshotRepoKey>
                    </publisher>
                    <buildInfo>
                      <buildName>GitLab-CI-Maven-demo</buildName>
                      <buildNumber>${env.CI_JOB_ID}</buildNumber>
                      <buildUrl>${env.CI_PROJECT_URL}/-/jobs/${env.CI_JOB_ID}</buildUrl>
                    </buildInfo>
                    <licenses>
                      <autoDiscover>true</autoDiscover>
                      <includePublishedArtifacts>false</includePublishedArtifacts>
                      <runChecks>true</runChecks>
                      <scopes>compile,runtime</scopes>
                      <violationRecipients>build@organisation.com</violationRecipients>
                    </licenses>
                  </configuration>
                </execution>
              </executions>
            </plugin>
          </plugins>   
```

For more configuration information see the [Maven Artifactory Plugin documentation](https://www.jfrog.com/confluence/display/RTF/Maven+Artifactory+Plugin).

#### Step 3:

Place a .gitlab-ci.yml in the root of your project, following this example.

#### Step 4:

You should be able to see published artifacts and build info in artifactory.

![screenshot](img/Screen_Shot2.png)

---
## Plugin documentation

The full plugin documentation is available [here](https://www.jfrog.com/confluence/display/RTF/Maven+Artifactory+Plugin).
