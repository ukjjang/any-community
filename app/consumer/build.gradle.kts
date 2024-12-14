plugins {
    id("com.anycommunity.app")
}

val serviceName = "consumer"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_KAFKA)

    implementation(project(":infra:kafka"))
    implementation(project(":infra:redis"))
    implementation(project(":util:jwt"))
}
