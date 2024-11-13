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
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_DATA_JPA)
    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)
}
