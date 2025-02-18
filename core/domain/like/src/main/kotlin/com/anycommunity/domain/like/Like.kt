package com.anycommunity.domain.like

import java.time.LocalDateTime
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.shared.BaseDomain

@ConsistentCopyVisibility
data class Like internal constructor(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val targetType: LikeType,
    val targetId: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Like) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode() = _id?.hashCode() ?: 0

    companion object {
        internal fun create(userId: Long, likeTarget: LikeTarget) = Like(
            userId = userId,
            targetType = likeTarget.type,
            targetId = likeTarget.id,
        )
    }
}
