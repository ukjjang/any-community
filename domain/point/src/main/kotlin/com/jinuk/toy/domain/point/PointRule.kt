package com.jinuk.toy.domain.point

import java.time.LocalDateTime
import com.jinuk.toy.infra.rdb.point.entity.PointRuleEntity
import com.jinuk.toy.util.domainhelper.BaseDomain

data class PointRule(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val ruleType: String,
    val amount: Long,
    val description: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PointRule) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode(): Int {
        return _id?.hashCode() ?: 0
    }
}

internal fun PointRuleEntity.toModel() =
    PointRule(
        _id = id,
        ruleType = ruleType,
        amount = amount,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun PointRule.toEntity() =
    PointRuleEntity(
        id = _id,
        ruleType = ruleType,
        amount = amount,
        description = description,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )