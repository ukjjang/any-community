package com.anycommunity.infra.mysql.outbox.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.definition.outbox.OutboxStatus
import com.anycommunity.infra.mysql.outbox.entity.OutboxEntity

interface OutboxEntityRepository : JpaRepository<OutboxEntity, Long> {
    fun findByStatusInOrderByIdDesc(statuses: Set<OutboxStatus>): List<OutboxEntity>
}
