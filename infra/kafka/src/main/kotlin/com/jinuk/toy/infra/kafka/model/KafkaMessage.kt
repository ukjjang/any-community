package com.jinuk.toy.infra.kafka.model

import java.time.LocalDateTime

data class KafkaMessage<T : Any>(
    val topic: String,
    val payload: T,
    val version: Int,
    val sentAt: String,
) {
    companion object {
        fun <T : Any> of(
            topic: KafkaTopic,
            payload: T,
        ) = KafkaMessage(
            topic = topic.name,
            version = topic.version,
            payload = payload,
            sentAt = LocalDateTime.now().toString(),
        )
    }
}
