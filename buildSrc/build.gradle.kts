plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven { url = uri("https://plugins.gradle.org/m2/") }
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

object Dependencies {
    private object Versions {
        const val KOTLIN = "2.1.0"
        const val KTLINT = "12.1.2"
        const val SPRING_BOOT = "3.3.5"
        const val SPRING_DEPENDENCY = "1.1.6"
        const val KSP = "$KOTLIN-1.0.29"
        const val KOVER = "0.8.3"
        const val SONARQUBE = "6.0.1.5171"
    }

    const val KOTLIN_GRADLE_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
    const val KOTLIN_NO_ARG = "org.jetbrains.kotlin:kotlin-noarg:${Versions.KOTLIN}"
    const val KOTLIN_ALL_OPEN = "org.jetbrains.kotlin:kotlin-allopen:${Versions.KOTLIN}"
    const val KSP = "com.google.devtools.ksp:com.google.devtools.ksp.gradle.plugin:${Versions.KSP}"
    const val KOVER = "org.jetbrains.kotlinx:kover-gradle-plugin:${Versions.KOVER}"

    const val KTLINT = "org.jlleitschuh.gradle:ktlint-gradle:${Versions.KTLINT}"
    const val SPRING_BOOT = "org.springframework.boot:spring-boot-gradle-plugin:${Versions.SPRING_BOOT}"
    const val SPRING_DEPENDENCY = "io.spring.gradle:dependency-management-plugin:${Versions.SPRING_DEPENDENCY}"
    const val SONARQUBE = "org.sonarsource.scanner.gradle:sonarqube-gradle-plugin:${Versions.SONARQUBE}"
}

dependencies {
    implementation(Dependencies.KOTLIN_GRADLE_PLUGIN)
    implementation(Dependencies.KOTLIN_NO_ARG)
    implementation(Dependencies.KOTLIN_ALL_OPEN)

    implementation(Dependencies.KOVER)
    implementation(Dependencies.KTLINT)
    implementation(Dependencies.SPRING_BOOT)
    implementation(Dependencies.SPRING_DEPENDENCY)
    implementation(Dependencies.SONARQUBE)

    runtimeOnly(Dependencies.KSP)
}
