import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.anycommunity.kotlin")
    id("com.anycommunity.spring-boot-jpa")
    id("com.anycommunity.integration-test")
    id("java-test-fixtures")
}

dependencies {
    api(project(":core:domain:shared"))

    implementation(project(":util:logger"))
    implementation(project(":util:object-mapper"))
    implementation(project(":util:custom-page"))

    implementation(project(":definition"))
    testImplementation(project(":definition"))
    testFixturesImplementation(project(":definition"))
    testFixturesImplementation(project(":util:faker"))

    implementation(project(":infra:mysql"))
    testImplementation(testFixtures(project(":infra:mysql")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_WEB)
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
