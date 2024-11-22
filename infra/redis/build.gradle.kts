plugins {
    id("com.anycommunity.lib")
    id("java-test-fixtures")
    id("com.anycommunity.integration-test")
}

dependencies {
    implementation(Dependencies.Redis.SPRING_BOOT_STARTER_REDIS)
    implementation(Dependencies.Redis.SPRING_BOOT_STARTER_REDISSON)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)

    implementation(project(":util:logger"))
    implementation(project(":util:object-mapper"))
    implementation(project(":util:custom-page"))
}
