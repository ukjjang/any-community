plugins {
    id("com.jinuk.toy.app")
    id("com.jinuk.toy.spring-boot-jpa")
}

val serviceName = "internal-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    api(project(":domain"))
    api(project(":infra:rdb"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
