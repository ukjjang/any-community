plugins {
    id("com.jinuk.toy.domain")
    id("com.jinuk.toy.querydsl")
}

dependencies {
    implementation(project(":infra:rdb"))
}
