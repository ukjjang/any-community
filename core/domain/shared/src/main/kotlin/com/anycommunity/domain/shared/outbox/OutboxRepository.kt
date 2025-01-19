package com.anycommunity.domain.shared.outbox

import org.springframework.stereotype.Repository
import com.anycommunity.definition.outbox.OutboxStatus
import com.anycommunity.infra.mysql.outbox.entity.OutboxEntity
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

private fun OutboxEntity.toModel() = Outbox(
    _id = id,
    topic = topic,
    payload = payload,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun Outbox.toEntity() = OutboxEntity(
    id = _id,
    topic = topic,
    payload = payload,
    status = status,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
