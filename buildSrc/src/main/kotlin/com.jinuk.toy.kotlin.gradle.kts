import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm")
    id("org.jetbrains.kotlinx.kover")
    id("org.jlleitschuh.gradle.ktlint")
}

repositories {
    mavenCentral()
}

val jdkVersion = "21"

dependencies {
    implementation(Dependencies.Kotlin.KOTLIN_REFLECT)
    implementation(Dependencies.Kotlin.KOTLIN_STDLIB)
    implementation(Dependencies.Kotlin.JACKSON_MODULE_KOTLIN)
    testImplementation(Dependencies.Kotlin.KOTLIN_TEST_JUNIT5)

    testImplementation(Dependencies.Kotlin.KOTLINX_COROUTINES_TEST)
    testImplementation(Dependencies.Kotest.RUNNER_JUNIT5)
    testImplementation(Dependencies.Kotest.ASSERTIONS_CORE)
    testImplementation(Dependencies.Kotest.ASSERTIONS_JSON)
    testImplementation(Dependencies.Kotest.PROPERTY)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(jdkVersion)
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget = JvmTarget.fromTarget(jdkVersion)
    }
}

kover.reports {
    filters.excludes {
        packages(
            "com.jinuk.toy.infra",
            "com.jinuk.toy.mvcapi",
        )
    }

    total {
        xml.onCheck = true
    }
}
