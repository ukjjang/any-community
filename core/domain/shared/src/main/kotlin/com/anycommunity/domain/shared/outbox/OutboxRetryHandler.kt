package com.anycommunity.domain.shared.outbox

import org.springframework.stereotype.Repository

@Repository
class OutboxRetryHandler(
    private val outboxRepository: OutboxRepository,
    private val outboxSender: OutboxSender,
) {
    fun retry() = outboxRepository.findRetryable().forEach {
        outboxSender.send(it)
    }
}
