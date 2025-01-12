plugins {
    id("org.sonarqube")
}

repositories {
    mavenCentral()
}

sonar {
    properties {
        property("sonar.projectKey", "any-community")
        property("sonar.projectName", "any-community")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/kover/report.xml")
    }
}
