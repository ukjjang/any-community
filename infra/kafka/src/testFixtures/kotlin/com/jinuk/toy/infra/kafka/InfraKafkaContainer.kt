package com.jinuk.toy.infra.kafka

import org.junit.Ignore
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
@Ignore
interface InfraKafkaContainer {
    companion object {
        private val kafka =
            KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.3.5"))
                .withKraft()

        @JvmStatic
        @DynamicPropertySource
        fun registerProperties(registry: DynamicPropertyRegistry) {
            registry.add("kafka.bootstrap-servers") { kafka.bootstrapServers }
        }

        init {
            kafka.start()
        }
    }
}
