package com.anycommunity.domain.shared.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class OutboxCreator(
    private val outboxRepository: OutboxRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
    private val objectMapper: ObjectMapper,
) {
    fun <T> create(topic: String, payload: T) {
        outboxRepository.save(Outbox.create(topic, objectMapper.writeValueAsString(payload)))
            .also { applicationEventPublisher.publishEvent(it) }
    }
}
