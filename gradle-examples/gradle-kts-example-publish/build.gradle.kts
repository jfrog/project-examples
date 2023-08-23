import org.jfrog.gradle.plugin.artifactory.task.ArtifactoryTask

plugins {
    java
    `maven-publish`
    id("com.jfrog.artifactory") version "5.+"
}

fun javaProjects() = subprojects.filter {
    File(it.projectDir, "src").isDirectory
}

val currentVersion: String by project

allprojects {
    apply(plugin = "com.jfrog.artifactory")

    group = "org.jfrog.test.gradle.publish"
    version = currentVersion
    status = "Integration"

    repositories {
        maven("http://127.0.0.1:8081/artifactory/libs-release")
    }
}

tasks.named<ArtifactoryTask>("artifactoryPublish") {
    skip = true
}

project("services") {
    tasks.named<ArtifactoryTask>("artifactoryPublish") {
        skip = true
    }
}

configure(javaProjects()) {
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    dependencies {
        testImplementation("junit:junit:4.7")
    }

    configure<PublishingExtension> {
        publications {
            register<MavenPublication>("mavenJava") {
                from(components.getByName("java"))
                artifact(file("$rootDir/gradle.properties"))
            }
        }
    }
}

project("api") {
    apply(plugin = "ivy-publish")

    configure<PublishingExtension> {
        publications {
            register<IvyPublication>("ivyJava") {
                from(components.getByName("java"))

                artifact(file("$rootDir/settings.gradle.kts")) {
                    name = "gradle-settings"
                    extension = "txt"
                    type = "text"
                }
                // The config below will add a extra attribute to the ivy.xml
                // See http://ant.apache.org/ivy/history/latest-milestone/concept.html#extra
                descriptor.withXml {
                    val info = asNode().get("info") as groovy.util.NodeList
                    val first = info.first() as groovy.util.Node
                    first.attributes()["e:architecture"] = "amd64"
                }
            }
        }
    }

    tasks.named<ArtifactoryTask>("artifactoryPublish") {
        publications(publishing.publications["ivyJava"])
    }
}

configure<org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention> {
    clientConfig.isIncludeEnvVars = true

    setContextUrl("http://127.0.0.1:8081/artifactory")
    publish {
        repository {
            setRepoKey("libs-snapshot-local") // The Artifactory repository key to publish to
            setUsername(providers.gradleProperty("artifactory_user").getOrNull()) // The publisher user name
            setPassword(providers.gradleProperty("artifactory_password").getOrNull()) // The publisher password
            // This is an optional section for configuring Ivy publication (when publishIvy = true).
            ivy {
                setIvyLayout("[organization]/[module]/ivy-[revision].xml")
                setArtifactLayout("[organization]/[module]/[revision]/[module]-[revision](-[classifier]).[ext]")
                setMavenCompatible(true) // Convert any dots in an [organization] layout value to path separators, similar to Maven"s groupId-to-path conversion. True if not specified
            }
        }

        defaults {
            // Reference to Gradle publications defined in the build script.
            // This is how we tell the Artifactory Plugin which artifacts should be
            // published to Artifactory.
            publications("mavenJava")
            setPublishArtifacts(true)
            // Properties to be attached to the published artifacts.
            setProperties(mapOf(
                "qa.level" to "basic",
                "dev.team" to "core"
            ))
            setPublishPom(true) // Publish generated POM files to Artifactory (true by default)
            setPublishIvy(true) // Publish generated Ivy descriptor files to Artifactory (true by default)
        }
    }
}

