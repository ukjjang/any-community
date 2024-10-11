plugins {
    id("com.jinuk.toy.api")
}

val serviceName = "external-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    api(project(":application"))
    api(project(":infra:rdb"))
}
