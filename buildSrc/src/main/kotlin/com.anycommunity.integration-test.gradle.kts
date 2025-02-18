import org.jetbrains.kotlin.gradle.plugin.KotlinCompilation

plugins {
    idea
    kotlin("jvm")
    id("org.sonarqube")
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

dependencies {
    "integrationTestImplementation"(project)

    testImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testImplementation(Dependencies.Kotest.EXTENSIONS_SPRING)
    testImplementation(Dependencies.Logcapture.KOTEST)
    testImplementation(Dependencies.Logcapture.CORE)
    testImplementation(Dependencies.Kotest.EXTENSIONS_NOW)
    testImplementation(Dependencies.Mockk.MOCKK)

    testImplementation(project(":util:faker"))
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

sonar {
    val testDir = file("src/test/kotlin")
    val integrationTestDir = file("src/integrationTest/kotlin")

    val testSources = mutableListOf<String>().also {
        testDir.isDirectory && it.add(testDir.path)
        integrationTestDir.isDirectory && it.add(integrationTestDir.path)
    }

    properties {
        property("sonar.tests", testSources)
    }
}
