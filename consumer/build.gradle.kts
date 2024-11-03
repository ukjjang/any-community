plugins {
    id("com.jinuk.toy.api")
}

val serviceName = "consumer"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(project(":util:jwt"))
}
