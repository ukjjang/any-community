package com.jinuk.toy.applicaiton

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
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.infra.kafka.InfraKafkaContainer
import com.jinuk.toy.infra.rdb.InfraRdbTestContainer
import com.jinuk.toy.infra.redis.InfraRedisContainer

@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = ["com.jinuk.toy"])
internal class IntegrationTestConfiguration

@Transactional
@ActiveProfiles(
    "test",
    "infra-rdb",
    "infra-redis",
    "infra-kafka",
    "util-jwt",
)
@ContextConfiguration(classes = [IntegrationTestConfiguration::class])
@SpringBootTest
internal interface IntegrationTest : InfraRdbTestContainer, InfraRedisContainer, InfraKafkaContainer

@Configuration
class KotestProjectConfig : AbstractProjectConfig() {
    override fun extensions() = listOf(SpringTestExtension(SpringTestLifecycleMode.Test))
}
