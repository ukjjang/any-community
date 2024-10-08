plugins {
    id("com.jinuk.toy.app")
}

val serviceName = "internal-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}
