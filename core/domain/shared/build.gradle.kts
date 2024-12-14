plugins {
    id("com.anycommunity.lib")
    id("com.anycommunity.spring-boot-jpa")
}

dependencies {
    implementation(project(":definition"))

    implementation(project(":infra:kafka"))
    implementation(project(":infra:mysql"))
}
