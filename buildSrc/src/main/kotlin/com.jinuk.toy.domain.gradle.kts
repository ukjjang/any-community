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
    implementation(project(":infra:rdb"))
    testImplementation(testFixtures(project(":infra:rdb")))

    testFixturesImplementation(project(":util:faker"))
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
