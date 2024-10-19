import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot-jpa")
    id("com.jinuk.toy.integration-test")
    id("java-test-fixtures")
}

dependencies {
    api(project(":domain:post"))
    api(project(":domain:user"))
    api(project(":domain:follow"))
    api(project(":domain:comment"))
    api(project(":domain:like"))
    testImplementation(testFixtures(project(":domain:post")))
    testImplementation(testFixtures(project(":domain:user")))
    testImplementation(testFixtures(project(":domain:follow")))
    testImplementation(testFixtures(project(":domain:comment")))
    testImplementation(testFixtures(project(":domain:like")))

    testImplementation(testFixtures(project(":infra:rdb")))

    implementation(project(":infra:redis"))
    testImplementation(testFixtures(project(":infra:redis")))

    api(project(":util:object-mapper"))
}


val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
