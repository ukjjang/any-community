plugins {
    id("org.sonarqube") version "6.0.1.5171"
}

sonar {
    properties {
        property("sonar.projectKey", "any-community")
        property("sonar.projectName", "any-community")
    }
}
