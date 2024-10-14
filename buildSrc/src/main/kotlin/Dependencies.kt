object Dependencies {
    object SpringBoot {
        const val SPRING_BOOT_STARTER_WEB = "org.springframework.boot:spring-boot-starter-web"
        const val SPRING_BOOT_STARTER_TEST = "org.springframework.boot:spring-boot-starter-test"
        const val SPRING_BOOT_STARTER_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
        const val SPRING_BOOT_STARTER_SECURITY = "org.springframework.boot:spring-boot-starter-security"

        const val JUNIT_PLATFORM_LAUNCHER = "org.junit.platform:junit-platform-launcher"
    }

    object Kotlin {
        const val KOTLINX_COROUTINES_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1"
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
        const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib"
        const val JACKSON_MODULE_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin"
        const val KOTLIN_TEST_JUNIT5 = "org.jetbrains.kotlin:kotlin-test-junit5"
    }

    object Kotest {
        private const val VERSION = "5.9.1"
        private const val EXTENSIONS_SPRING_VERSION = "1.3.0"

        const val RUNNER_JUNIT5 = "io.kotest:kotest-runner-junit5:$VERSION"
        const val ASSERTIONS_CORE = "io.kotest:kotest-assertions-core:$VERSION"
        const val ASSERTIONS_JSON = "io.kotest:kotest-assertions-json:$VERSION"
        const val PROPERTY = "io.kotest:kotest-property:$VERSION"
        const val EXTENSIONS_SPRING =
            "io.kotest.extensions:kotest-extensions-spring:$EXTENSIONS_SPRING_VERSION"
        const val EXTENSIONS_NOW = "io.kotest:kotest-extensions-now:$VERSION"
    }

    object TestContainers {
        private const val TEST_CONTAINERS_VERSION = "1.20.0"

        const val JUNIT_JUPITER = "org.testcontainers:junit-jupiter:$TEST_CONTAINERS_VERSION"
        const val MYSQL = "org.testcontainers:mysql:$TEST_CONTAINERS_VERSION"
    }

    object Mysql {
        const val CONNECTOR = "com.mysql:mysql-connector-j"
    }

    object Logcapture {
        private const val VERSION = "1.3.3"

        const val KOTEST = "org.logcapture:logcapture-kotest:${VERSION}"
        const val CORE = "org.logcapture:logcapture-core:${VERSION}"
    }

    object Openapi {
        private const val VERSION = "2.6.0"

        const val WEBMVC_UI = "org.springdoc:springdoc-openapi-starter-webmvc-ui:${VERSION}"
        const val COMMON = "org.springdoc:springdoc-openapi-starter-common:${VERSION}"
    }

    object JJWT {
        private const val VERSION = "0.11.5"

        const val API = "io.jsonwebtoken:jjwt-api:$VERSION"
        const val IMPL = "io.jsonwebtoken:jjwt-impl:$VERSION"
        const val JACKSON = "io.jsonwebtoken:jjwt-jackson:$VERSION"
    }

    object JBcrypt {
        private const val VERSION = "0.4"

        const val JBCRYPT = "org.mindrot:jbcrypt:$VERSION"
    }

    object Faker {
        private const val VERSION = "2.2.2"

        const val FAKER = "net.datafaker:datafaker:$VERSION"
    }
}
