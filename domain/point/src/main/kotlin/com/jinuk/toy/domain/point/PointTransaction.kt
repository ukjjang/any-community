package com.jinuk.toy.domain.point

import java.time.LocalDateTime
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.infra.rdb.point.entity.PointTransactionEntity

@ConsistentCopyVisibility
data class PointTransaction internal constructor(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val amount: Point,
    val description: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PointTransaction) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode() = _id?.hashCode() ?: 0

    companion object {
        internal fun create(info: PointTransactionCreateInfo) = PointTransaction(
            userId = info.userId,
            amount = info.amount,
            description = info.description,
        )
    }
}

internal fun PointTransactionEntity.toModel() = PointTransaction(
    _id = id,
    userId = userId,
    amount = amount,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun PointTransaction.toEntity() = PointTransactionEntity(
    id = _id,
    userId = userId,
    amount = amount,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
