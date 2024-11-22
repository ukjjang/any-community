plugins {
    id("com.anycommunity.lib")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":definition"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_KAFKA)
    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.KAFKA)
}
