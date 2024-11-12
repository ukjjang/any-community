plugins {
    id("com.jinuk.toy.api")
}

val serviceName = "consumer"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_KAFKA)

    implementation(project(":infra:kafka"))
    implementation(project(":common:util:jwt"))
}
