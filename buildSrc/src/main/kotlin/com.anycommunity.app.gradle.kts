import gradle.kotlin.dsl.accessors._53aec52b18a06826989757eb5753d77a.implementation

plugins {
    id("com.anycommunity.kotlin")
    id("com.anycommunity.spring-boot")
    id("com.anycommunity.integration-test")
    id("com.anycommunity.domain-collection")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":core:usecase"))
    implementation(project(":definition"))
    implementation(project(":util:custom-page"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_DATA_COMMONS)
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_ACTUATOR)
}
