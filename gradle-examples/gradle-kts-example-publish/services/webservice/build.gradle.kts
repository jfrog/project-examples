plugins {
    war
}

dependencies {
    implementation(project(":shared"))
    implementation("commons-collections:commons-collections:3.2@jar")
    implementation("commons-io:commons-io:1.2")
    implementation("commons-lang:commons-lang:2.4@jar")
    implementation("org.apache.wicket", "wicket", "1.3.7")
    implementation(project(":api"))
}
