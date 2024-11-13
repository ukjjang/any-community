import gradle.kotlin.dsl.accessors._53aec52b18a06826989757eb5753d77a.implementation

plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot")
    id("com.jinuk.toy.integration-test")
    id("com.jinuk.toy.domain-collection")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":application"))
    implementation(project(":common:value"))
    implementation(project(":common:util:custom-page"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_DATA_COMMONS)
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_ACTUATOR)
}
