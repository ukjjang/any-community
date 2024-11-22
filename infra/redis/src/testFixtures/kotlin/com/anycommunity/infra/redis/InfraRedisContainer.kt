package com.anycommunity.infra.redis

import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
interface InfraRedisContainer {
    companion object {
        private const val DOCKER_REDIS_IMAGE = "redis:7.0.8-alpine"
        private const val REDIS_PORT = 6379

        private val redis = GenericContainer<Nothing>(DOCKER_REDIS_IMAGE).apply {
            withExposedPorts(REDIS_PORT)
            withReuse(true)
        }

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.data.redis.host") { redis.host }
            registry.add("spring.data.redis.port") { redis.getMappedPort(REDIS_PORT).toString() }
        }

        init {
            redis.start()
        }
    }
}
