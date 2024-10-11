plugins {
    kotlin("plugin.jpa")

    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
}
