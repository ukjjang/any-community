package com.jinuk.toy.domain.like

import java.time.LocalDateTime
import com.jinuk.toy.infra.rdb.like.entity.LikeEntity
import com.jinuk.toy.util.domainhelper.BaseDomain

data class Like(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val targetType: LikeType,
    val targetId: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    companion object {
        fun create(
            userId: Long,
            likeTarget: LikeTarget,
        ) = Like(
            userId = userId,
            targetType = likeTarget.type,
            targetId = likeTarget.id,
        )
    }
}

internal fun LikeEntity.toModel() =
    Like(
        _id = id,
        userId = userId,
        targetType = LikeType.from(targetType),
        targetId = targetId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun Like.toEntity() =
    LikeEntity(
        id = _id,
        userId = userId,
        targetType = targetType.name,
        targetId = targetId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
