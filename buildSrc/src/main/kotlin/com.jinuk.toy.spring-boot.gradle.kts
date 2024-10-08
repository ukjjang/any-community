plugins {
    kotlin("jvm")

    id("org.springframework.boot")
    id("io.spring.dependency-management")

    kotlin("plugin.spring")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    jvmArgs("-XX:+EnableDynamicAgentLoading")

    useJUnitPlatform()
    maxHeapSize = "4096m"
}
