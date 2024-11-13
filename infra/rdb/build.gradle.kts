plugins {
    id("com.jinuk.toy.lib")
    id("com.jinuk.toy.spring-boot-jpa")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":common:value"))

    runtimeOnly(Dependencies.Mysql.CONNECTOR)

    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.MYSQL)
}
