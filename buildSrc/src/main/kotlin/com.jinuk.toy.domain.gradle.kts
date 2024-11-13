import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot-jpa")
    id("com.jinuk.toy.integration-test")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":common:util:domain-helper"))

    implementation(project(":common:util:logger"))
    implementation(project(":common:util:object-mapper"))
    implementation(project(":common:util:custom-page"))

    implementation(project(":common:value"))
    testFixturesImplementation(project(":common:value"))
    testFixturesImplementation(project(":common:util:faker"))

    implementation(project(":infra:rdb"))
    testImplementation(testFixtures(project(":infra:rdb")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_WEB)
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
