package com.anycommunity.domain.shared.outbox

import org.springframework.stereotype.Repository
import com.anycommunity.definition.outbox.OutboxStatus
import com.anycommunity.infra.mysql.outbox.jpa.OutboxEntityRepository

@Repository
class OutboxRepository(
    private val outboxEntityRepository: OutboxEntityRepository,
) {
    companion object {
        private val RETRY_TARGET_STATUS = setOf(OutboxStatus.INIT, OutboxStatus.FAIL)
    }

    fun save(outbox: Outbox) = outboxEntityRepository.save(outbox.toEntity()).toModel()

    fun findRetryable() = outboxEntityRepository.findByStatusInOrderByIdDesc(RETRY_TARGET_STATUS)
        .map { it.toModel() }
}
