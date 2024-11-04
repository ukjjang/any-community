package com.jinuk.toy.infra.kafka.model

import java.time.LocalDateTime

data class KafkaMessage<T>(
    val topic: String,
    val payload: T,
    val sentAt: String,
) {
    companion object {
        fun <T : Any> of(
            topic: String,
            payload: T,
        ) = KafkaMessage(
            topic = topic,
            payload = payload,
            sentAt = LocalDateTime.now().toString(),
        )
    }
}
