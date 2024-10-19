plugins {
    id("com.jinuk.toy.api")
}

val serviceName = "external-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(project(":util:jwt"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_SECURITY)
}
