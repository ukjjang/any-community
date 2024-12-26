package com.anycommunity.domain.user_feed

import java.time.LocalDateTime
import com.anycommunity.domain.shared.BaseDomain
import com.anycommunity.infra.mysql.user_feed.entity.UserFeedEntity

@ConsistentCopyVisibility
data class UserFeed internal constructor(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val postId: Long,
    val postAuthorId: Long,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UserFeed) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode() = _id?.hashCode() ?: 0

    companion object {
        internal fun create(info: UserFeedCreateInfo) = UserFeed(
            userId = info.userId,
            postId = info.postId,
            postAuthorId = info.postAuthorId,
        )
    }
}

internal fun UserFeedEntity.toModel() = UserFeed(
    _id = id,
    userId = userId,
    postId = postId,
    postAuthorId = postAuthorId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun UserFeed.toEntity() = UserFeedEntity(
    id = _id,
    userId = userId,
    postId = postId,
    postAuthorId = postAuthorId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
