plugins {
    id("com.jinuk.toy.lib")
    id("java-test-fixtures")
    id("com.jinuk.toy.integration-test")
}

dependencies {
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_REDIS)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)

    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_REDISSON)

    implementation(project(":common:util:logger"))
    implementation(project(":common:util:object-mapper"))
    implementation(project(":common:util:custom-page"))
}
