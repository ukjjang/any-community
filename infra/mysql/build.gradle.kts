plugins {
    id("com.anycommunity.lib")
    id("com.anycommunity.spring-boot-jpa")
    id("java-test-fixtures")
}

dependencies {
    implementation(project(":definition"))
    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)

    runtimeOnly(Dependencies.Mysql.CONNECTOR)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.MYSQL)
}


/*

plugins {
    id("com.anycommunity.lib")
    id("com.anycommunity.spring-boot-jpa")
    id("java-test-fixtures")
    id("dev.monosoul.jooq-docker")
}

// plugin implementation("dev.monosoul.jooq-docker:dev.monosoul.jooq-docker.gradle.plugin:6.1.11")
dependencies {
    implementation(project(":definition"))

    runtimeOnly(Dependencies.Mysql.CONNECTOR)

    implementation(Dependencies.KDSL.KOTLIN_JDSL_JAKARATA)

    testFixturesImplementation(Dependencies.SpringBoot.SPRING_BOOT_STARTER_TEST)
    testFixturesImplementation(Dependencies.TestContainers.JUNIT_JUPITER)
    testFixturesImplementation(Dependencies.TestContainers.MYSQL)

    // jOOQ
    api("org.springframework.boot:spring-boot-starter-jooq")
    // implementation("org.jooq:jooq:3.19.15")
    // implementation("org.jooq:jooq-codegen:3.19.15")
    // jooqCodegen("org.flywaydb:flyway-core:10.22.0")
    jooqCodegen("org.flywaydb:flyway-mysql:10.22.0")
    jooqCodegen(Dependencies.Mysql.CONNECTOR)

    // runtimeOnly("io.asyncer:r2dbc-mysql:1.2.0")
    // implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:3.3.2")
}

jooq {
    withContainer {
        image {
            name = "mysql:8.0.29"
            envVars = mapOf(
                "MYSQL_ROOT_PASSWORD" to "mysql",
                "MYSQL_DATABASE" to "mysql",
            )
        }
        db {
            username = "root"
            password = "mysql"
            name = "mysql"
            port = 3306
            jdbc {
                schema = "jdbc:mysql"
                driverClassName = "com.mysql.cj.jdbc.Driver"
            }
        }
    }
}

// tasks {
//     generateJooqClasses {
//         schemas.set(listOf("dsl"))
//         migrationLocations.setFromFilesystem("src/main/resources/db/migration")
//         basePackageName.set("com.anycommunity.infra.mysql")
//         outputDirectory.set(project.layout.buildDirectory.dir("generated/jooq"))
//
//         usingJavaConfig {
//             generate.apply {
//                 isDaos = true
//                 isDeprecated = false
//                 isRecords = true
//                 isImmutablePojos = true
//                 isFluentSetters = true
//                 isJavaTimeTypes = true
//             }
//             strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
//             database.withExcludes("flyway_schema_history")
//         }
//     }
// }

// https://sjiwon-dev.tistory.com/51


 */
