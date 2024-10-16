package com.jinuk.toy.domain.like

import com.jinuk.toy.infra.rdb.like.entity.LikeEntity
import com.jinuk.toy.util.domainhelper.BaseDomain
import java.time.LocalDateTime

data class Like(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),

    val userId: Long,
    val targetType: LikeType,
    val targetId: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()
}

internal fun LikeEntity.toModel() = Like(
    _id = id,
    userId = userId,
    targetType = LikeType.from(targetType),
    targetId = targetId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun Like.toEntity() = LikeEntity(
    id = _id,
    userId = userId,
    targetType = targetType.name,
    targetId = targetId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
