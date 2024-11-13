import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot-jpa")
    id("com.jinuk.toy.integration-test")
    id("java-test-fixtures")
}

dependencies {
    api(project(":common:value"))

    api(project(":common:util:logger"))
    api(project(":common:util:domain-helper"))
    api(project(":common:util:object-mapper"))
    api(project(":common:util:custom-page"))

    implementation(project(":infra:rdb"))
    testImplementation(testFixtures(project(":infra:rdb")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    testFixturesImplementation(project(":common:util:faker"))
    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_WEB)
    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
