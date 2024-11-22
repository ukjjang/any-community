package com.anycommunity.infra.kafka.service

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import com.anycommunity.infra.kafka.model.KafkaMessage

@Service
class KafkaProducer(
    private val kafkaStringTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
) {
    fun <T : Any> send(topic: String, key: String, payload: T) {
        KafkaMessage.of(topic, payload).let {
            kafkaStringTemplate.send(topic, key, objectMapper.writeValueAsString(it))
        }
    }

    fun <T : Any> send(topic: String, payload: T) {
        KafkaMessage.of(topic, payload).let {
            kafkaStringTemplate.send(topic, objectMapper.writeValueAsString(it))
        }
    }
}
