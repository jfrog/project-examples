val spi: Configuration by configurations.creating

dependencies {
    implementation(project(":shared"))
    implementation("commons-lang:commons-lang:2.4")
    implementation("commons-io:commons-io:1.2")
    implementation("org.apache.wicket", "wicket", "1.3.7")
}

// Just a smoke test that using this option does not lead to any exception
tasks {
    named<JavaCompile>("compileJava") {
        options.compilerArgs = listOf("-Xlint:unchecked")
    }
}
