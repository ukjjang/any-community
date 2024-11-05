import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot-jpa")
    id("com.jinuk.toy.integration-test")
    id("java-test-fixtures")
}

dependencies {
    api(project(":constant"))

    api(project(":util:logger"))
    api(project(":util:domain-helper"))
    api(project(":util:object-mapper"))
    api(project(":util:custom-page"))

    implementation(project(":infra:rdb"))
    testImplementation(testFixtures(project(":infra:rdb")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    testFixturesImplementation(project(":util:faker"))
    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_WEB)
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
