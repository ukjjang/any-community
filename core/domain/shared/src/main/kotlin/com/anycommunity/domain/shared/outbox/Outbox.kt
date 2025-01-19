package com.anycommunity.domain.shared.outbox

import java.time.LocalDateTime
import com.anycommunity.definition.outbox.OutboxStatus
import com.anycommunity.domain.shared.BaseDomain

@ConsistentCopyVisibility
data class Outbox internal constructor(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val topic: String,
    val payload: String,
    val status: OutboxStatus = OutboxStatus.INIT,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Outbox) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode() = _id?.hashCode() ?: 0

    internal fun success() = copy(status = OutboxStatus.SUCCESS)
    internal fun fail() = copy(status = OutboxStatus.FAIL)

    companion object {
        internal fun create(topic: String, payload: String) = Outbox(
            topic = topic,
            payload = payload,
        )
    }
}
