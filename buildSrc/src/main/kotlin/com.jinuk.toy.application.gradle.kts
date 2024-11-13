import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot-jpa")
    id("com.jinuk.toy.integration-test")
    id("com.jinuk.toy.domain-collection")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":common:value"))

    implementation(project(":common:util:custom-page"))
    implementation(project(":common:util:object-mapper"))

    testImplementation(testFixtures(project(":infra:rdb")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    implementation(project(":infra:kafka"))
    testImplementation(testFixtures(project(":infra:kafka")))
}


val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
