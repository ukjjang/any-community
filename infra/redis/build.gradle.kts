plugins {
    id("com.jinuk.toy.lib")
    id("java-test-fixtures")
}

dependencies {
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_REDIS)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
}