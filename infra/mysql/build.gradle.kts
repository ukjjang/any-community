plugins {
    id("com.anycommunity.lib")
    id("com.anycommunity.spring-boot-jpa")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":definition"))

    runtimeOnly(Dependencies.Mysql.CONNECTOR)

    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.MYSQL)
}
