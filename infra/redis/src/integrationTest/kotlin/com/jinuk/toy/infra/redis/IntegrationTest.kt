package com.jinuk.toy.infra.redis

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = ["com.jinuk.toy"])
internal class IntegrationTestConfiguration

@ActiveProfiles(
    "test",
    "infra-redis",
)
@ContextConfiguration(classes = [IntegrationTestConfiguration::class])
@SpringBootTest
internal interface IntegrationTest : InfraRedisContainer
