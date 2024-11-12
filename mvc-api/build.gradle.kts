plugins {
    id("com.jinuk.toy.api")
    id("com.jinuk.toy.springdoc-openapi")
}

val serviceName = "mvc-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(project(":common:util:jwt"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_SECURITY)
}
