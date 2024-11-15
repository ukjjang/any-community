package com.jinuk.toy.domain.point

import java.time.LocalDateTime
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.infra.rdb.point.entity.PointGameProbabilityEntity

data class PointGameProbability(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val point: Point,
    val probability: Int,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PointGameProbability) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode(): Int = _id?.hashCode() ?: 0
}

internal fun PointGameProbabilityEntity.toModel() = PointGameProbability(
    _id = id,
    point = point,
    probability = probability,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun PointGameProbability.toEntity() = PointGameProbabilityEntity(
    id = _id,
    point = point,
    probability = probability,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
