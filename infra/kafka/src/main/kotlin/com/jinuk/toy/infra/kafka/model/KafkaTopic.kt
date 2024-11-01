package com.jinuk.toy.infra.kafka.model

enum class KafkaTopic(
    val topicName: String,
    val version: Int,
) {
    DEAD_LETTER("dead-letter", 1),
}
