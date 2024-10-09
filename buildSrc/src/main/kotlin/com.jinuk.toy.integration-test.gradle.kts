import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

plugins {
    idea
    kotlin("jvm")
}

val integrationTest: SourceSet by sourceSets.creating

configurations[integrationTest.implementationConfigurationName].extendsFrom(configurations.testImplementation.get())
configurations[integrationTest.runtimeOnlyConfigurationName].extendsFrom(configurations.testRuntimeOnly.get())

val integrationTestTask = tasks.register<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"
    useJUnitPlatform()

    testClassesDirs = integrationTest.output.classesDirs
    classpath = configurations[integrationTest.runtimeClasspathConfigurationName] + integrationTest.output

    jvmArgs("-XX:+EnableDynamicAgentLoading")
    shouldRunAfter(tasks.test)
}

tasks.check {
    dependsOn(integrationTestTask)
}

val kotestVersion = "5.9.1"
val kotestExtensionsSpringVersion = "1.3.0"
val logcaptureVersion = "1.3.3"

dependencies {
    "integrationTestImplementation"(project)

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:${kotestExtensionsSpringVersion}")
    testImplementation("org.logcapture:logcapture-kotest:${logcaptureVersion}")
    testImplementation("org.logcapture:logcapture-core:${logcaptureVersion}")
    testImplementation("io.kotest:kotest-extensions-now:${kotestVersion}")
}

kotlin.target.compilations {
    getByName("integrationTest") {
        associateWith(getByName(KotlinCompilation.MAIN_COMPILATION_NAME))
    }
}

idea {
    module {
        testSources.setFrom(
            integrationTest.kotlin.srcDirs,
            integrationTest.resources.srcDirs,
        )
    }
}
