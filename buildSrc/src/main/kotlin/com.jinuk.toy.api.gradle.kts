plugins {
    id("com.jinuk.toy.kotlin")
    id("com.jinuk.toy.spring-boot")
    id("com.jinuk.toy.integration-test")
}

dependencies {
    implementation(project(":application"))

    implementation(Dependencies.SpringBoot.SPRING_BOOT_DATA_COMMONS)
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_ACTUATOR)
}
