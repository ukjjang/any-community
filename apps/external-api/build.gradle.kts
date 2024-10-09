plugins {
    id("com.jinuk.toy.app")
    id("com.jinuk.toy.spring-boot-jpa")
}

val serviceName = "external-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    api(project(":domain:post"))
    api(project(":infra:rdb"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
