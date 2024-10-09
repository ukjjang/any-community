plugins {
    id("com.jinuk.toy.domain")
    id("com.jinuk.toy.querydsl")
    id("com.jinuk.toy.integration-test")
}

dependencies {
    implementation(project(":infra:rdb"))

    testImplementation(testFixtures(project(":infra:rdb")))
    testFixturesImplementation(project(":util:faker"))
}
