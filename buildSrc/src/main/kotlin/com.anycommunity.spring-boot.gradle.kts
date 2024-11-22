plugins {
    kotlin("jvm")

    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("plugin.spring")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_WEB)

    testImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testRuntimeOnly(Dependencies.SpringBoot.JUNIT_PLATFORM_LAUNCHER)
}

tasks.withType<Test> {
    jvmArgs("-XX:+EnableDynamicAgentLoading")

    useJUnitPlatform()
    maxHeapSize = "4096m"
}
