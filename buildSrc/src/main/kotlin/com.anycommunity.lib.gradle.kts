import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("com.anycommunity.kotlin")
    id("com.anycommunity.spring-boot")
}

val bootJar: BootJar by tasks
val jar: Jar by tasks

bootJar.enabled = false
jar.enabled = true
