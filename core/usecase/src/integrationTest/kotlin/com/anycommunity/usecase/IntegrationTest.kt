package com.anycommunity.usecase

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import com.anycommunity.infra.kafka.InfraKafkaContainer
import com.anycommunity.infra.mysql.InfraMysqlTestContainer
import com.anycommunity.infra.redis.InfraRedisContainer

@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = ["com.anycommunity"])
internal class IntegrationTestConfiguration

@ActiveProfiles(
    "test",
    "infra-mysql",
    "infra-redis",
    "infra-kafka",
    "util-jwt",
)
@ContextConfiguration(classes = [IntegrationTestConfiguration::class])
@SpringBootTest
internal interface IntegrationTest : InfraMysqlTestContainer, InfraRedisContainer, InfraKafkaContainer

@Configuration
class KotestProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))
}
