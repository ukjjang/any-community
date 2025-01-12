plugins {
    id("com.anycommunity.lib")
    id("com.anycommunity.spring-boot-jpa")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":definition"))
    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)

    runtimeOnly(Dependencies.Mysql.CONNECTOR)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.MYSQL)
}
