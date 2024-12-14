package com.anycommunity.domain.shared.outbox

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import com.anycommunity.domain.shared.config.AsyncConfig.Companion.OUTBOX_POOL

@Component
class OutboxEventListener(
    private val outboxSender: OutboxSender,
) {

    @Async(OUTBOX_POOL)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun send(outbox: Outbox) {
        outboxSender.send(outbox)
    }
}
