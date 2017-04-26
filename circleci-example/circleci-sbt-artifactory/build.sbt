lazy val root = (project in file(".")).
  settings(
    name := "SBT_Example",
    version := "1.1-SNAPSHOT",
    scalaVersion := "2.11.4"
  )

  resolvers += "Artifactory" at "https://gcartifactory-us.jfrog.info/artifactory/sbt-virtual"

  publishTo := Some("Artifactory Realm" at "https://gcartifactory-us.jfrog.info/artifactory/sbt-dev;build.timestamp=" + new java.util.Date().getTime)

  credentials += Credentials(new File("credentials.properties"))

  libraryDependencies += "org.apache.derby" % "derby" % "10.4.1.3"