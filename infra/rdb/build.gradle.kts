import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.spring-boot-jpa")
    id("java-test-fixtures")
}

dependencies {
    runtimeOnly(Dependencies.Mysql.CONNECTOR)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.MYSQL)
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
