import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.anycommunity.kotlin")
    id("com.anycommunity.spring-boot-jpa")
    id("com.anycommunity.integration-test")
    id("com.anycommunity.domain-collection")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":definition"))

    implementation(project(":util:custom-page"))
    implementation(project(":util:object-mapper"))

    testImplementation(testFixtures(project(":infra:mysql")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    implementation(project(":infra:kafka"))
    testImplementation(testFixtures(project(":infra:kafka")))
}


val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
