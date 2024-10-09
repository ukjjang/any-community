plugins {
    id("com.jinuk.toy.lib")
    id("com.jinuk.toy.querydsl")
    id("java-test-fixtures")
}

val testContainersVersion = "1.20.0"

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")

    testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
    testFixturesImplementation("org.testcontainers:junit-jupiter:${testContainersVersion}")
    testFixturesImplementation("org.testcontainers:mysql:$testContainersVersion")
}
