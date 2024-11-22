plugins {
    id("com.anycommunity.app")
    id("com.anycommunity.springdoc-openapi")
}

val serviceName = "mvc-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(project(":util:jwt"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_SECURITY)
}
