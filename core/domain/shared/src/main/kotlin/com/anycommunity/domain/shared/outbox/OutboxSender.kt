package com.anycommunity.domain.shared.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Repository
import com.anycommunity.infra.kafka.service.KafkaProducer

@Repository
class OutboxSender(
    private val outboxRepository: OutboxRepository,
    private val kafkaProducer: KafkaProducer,
    private val objectMapper: ObjectMapper,
) {
    fun send(outbox: Outbox) {
        runCatching {
            val parsedPayload = objectMapper.readValue<Map<String, String>>(outbox.payload)
            kafkaProducer.send(outbox.topic, parsedPayload)
        }.onSuccess {
            outboxRepository.save(outbox.success())
        }.onFailure {
            outboxRepository.save(outbox.fail())
        }
    }
}
