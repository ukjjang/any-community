plugins {
    id("com.jinuk.toy.api")
}

val serviceName = "external-api"

tasks.bootJar {
    archiveBaseName.set(serviceName)
}

dependencies {
    implementation(project(":util:jwt"))

    implementation("org.springframework.boot:spring-boot-starter-security")

}
