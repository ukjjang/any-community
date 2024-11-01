package com.jinuk.toy.infra.kafka

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaProperty(
    @Value("\${kafka.bootstrap-servers}")
    val bootstrapServers: String,
    @Value("\${kafka.security.protocol}")
    val securityProtocol: String,
    @Value("\${kafka.offset-reset}")
    val offsetReset: String,
)
