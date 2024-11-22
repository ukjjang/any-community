package com.anycommunity.infra.mysql

import org.junit.Ignore
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.containers.Network
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile
import java.time.Duration

@Testcontainers
@Ignore
interface InfraMysqlTestContainer {
    companion object {
        private val NET_WORK = Network.newNetwork()
        private const val NET_WORK_ALIASES = "anycommunity-network"
        private const val DB_NAME = "anycommunity-network"
        private const val DB_USERNAME = "user"
        private const val DB_PASSWORD = "password"

        private val DB = DockerImageName.parse("mysql/mysql-server:8.0.26")
            .asCompatibleSubstituteFor("mysql")
            .let { compatibleImageName -> MySQLContainer<Nothing>(compatibleImageName) }
            .apply {
                withDatabaseName(DB_NAME)
                withUsername("user")
                withPassword("password")
                withNetwork(NET_WORK)
                withNetworkAliases(NET_WORK_ALIASES)
                withCommand("mysqld", "--sql_mode=")
            }

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("datasource.mysql.writer.jdbc-url") { DB.jdbcUrl }
            registry.add("datasource.mysql.writer.username") { DB_USERNAME }
            registry.add("datasource.mysql.writer.password") { DB_PASSWORD }

            registry.add("datasource.mysql.reader.jdbc-url") { DB.jdbcUrl }
            registry.add("datasource.mysql.reader.username") { DB_USERNAME }
            registry.add("datasource.mysql.reader.password") { DB_PASSWORD }
        }

        init {
            DB.start()

            GenericContainer(DockerImageName.parse("flyway/flyway:10"))
                .withCopyFileToContainer(
                    MountableFile.forClasspathResource("db/flyway.conf", 777),
                    "/flyway/conf/flyway.conf",
                )
                .withCopyFileToContainer(
                    MountableFile.forClasspathResource("db/migration", 777),
                    "/flyway/sql",
                )
                .withNetwork(NET_WORK)
                .withEnv("FLYWAY_DB_USER", DB_USERNAME)
                .withEnv("FLYWAY_DB_PASSWORD", DB_PASSWORD)
                .withEnv("FLYWAY_DB_URL", "jdbc:mysql://$NET_WORK_ALIASES:3306/$DB_NAME")
                .waitingFor(
                    Wait.forLogMessage("(.*Successfully applied.*)", 1)
                        .withStartupTimeout(Duration.ofSeconds(30)),
                )
                .withCommand("migrate")
                .start()
        }
    }
}
