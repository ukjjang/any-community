package com.anycommunity.domain.follow

import java.time.LocalDateTime
import com.anycommunity.domain.shared.BaseDomain

@ConsistentCopyVisibility
data class Follow internal constructor(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val followerUserId: Long,
    val followingUserId: Long,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Follow) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode() = _id?.hashCode() ?: 0

    companion object {
        internal fun create(followRelation: FollowRelation) = Follow(
            followerUserId = followRelation.followerUserId,
            followingUserId = followRelation.followingUserId,
        )
    }
}
