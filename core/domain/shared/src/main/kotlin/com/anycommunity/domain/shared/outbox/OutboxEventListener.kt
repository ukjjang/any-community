package com.anycommunity.domain.shared.outbox

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import com.anycommunity.domain.shared.config.AsyncConfig.Companion.OUTBOX_POOL

@Component
class OutboxEventListener(
    private val outboxSender: OutboxSender,
) {

    @Async(OUTBOX_POOL)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun send(outbox: Outbox) {
        outboxSender.send(outbox)
    }
}
