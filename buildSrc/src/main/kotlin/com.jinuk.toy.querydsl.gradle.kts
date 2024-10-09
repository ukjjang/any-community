plugins {
    id("com.jinuk.toy.spring-boot-jpa")
    kotlin("kapt")
}

val querydslVersion = "5.0.0"

dependencies {
    implementation("com.querydsl:querydsl-jpa:${querydslVersion}:jakarta")
    kapt("com.querydsl:querydsl-apt:${querydslVersion}:jakarta")
}
