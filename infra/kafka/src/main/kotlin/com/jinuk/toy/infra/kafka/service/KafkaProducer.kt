package com.jinuk.toy.infra.kafka.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jinuk.toy.infra.kafka.model.KafkaMessage
import com.jinuk.toy.infra.kafka.model.KafkaTopic
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducer(
    private val kafkaStringTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {
    fun <T : Any> send(
        topic: KafkaTopic,
        key: String,
        payload: T,
    ) {
        KafkaMessage.of(topic, payload).let {
            kafkaStringTemplate.send(topic.name, key, objectMapper.writeValueAsString(it))
        }
    }

    fun <T : Any> send(
        topic: KafkaTopic,
        payload: T,
    ) {
        KafkaMessage.of(topic, payload).let {
            kafkaStringTemplate.send(topic.name, objectMapper.writeValueAsString(it))
        }
    }
}
