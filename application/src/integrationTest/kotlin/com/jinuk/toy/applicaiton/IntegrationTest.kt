package com.jinuk.toy.applicaiton

import com.jinuk.toy.infra.rdb.InfraRdbTestContainer
import com.jinuk.toy.infra.redis.InfraRedisContainer
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.transaction.annotation.Transactional

@EnableAutoConfiguration(exclude = [DataSourceAutoConfiguration::class])
@ComponentScan(basePackages = ["com.jinuk.toy"])
internal class IntegrationTestConfiguration

@Transactional
@ActiveProfiles(
    "test",
    "infra-rdb",
    "infra-redis",
    "util-jwt",
)
@ContextConfiguration(classes = [IntegrationTestConfiguration::class])
@SpringBootTest
internal interface IntegrationTest : InfraRdbTestContainer, InfraRedisContainer
