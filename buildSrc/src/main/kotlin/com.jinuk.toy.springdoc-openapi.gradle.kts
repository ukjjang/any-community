import org.gradle.kotlin.dsl.kotlin

val openApiVersion = "2.6.0"

plugins {
    kotlin("jvm")
}

dependencies {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${openApiVersion}")
    implementation("org.springdoc:springdoc-openapi-starter-common:${openApiVersion}")
}
