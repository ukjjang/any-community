import gradle.kotlin.dsl.accessors._458d78406d6ada6c312d6c9e71140b0b.api
import gradle.kotlin.dsl.accessors._458d78406d6ada6c312d6c9e71140b0b.implementation
import gradle.kotlin.dsl.accessors._458d78406d6ada6c312d6c9e71140b0b.testImplementation
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot-jpa")
    id("com.jinuk.toy.integration-test")
    id("java-test-fixtures")
}

dependencies {
    api(project(":util:logger"))
    api(project(":util:domain-helper"))

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
