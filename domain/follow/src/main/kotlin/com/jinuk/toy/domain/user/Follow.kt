package com.jinuk.toy.domain.user

import java.time.LocalDateTime
import com.jinuk.toy.infra.rdb.follow.entity.FollowEntity
import com.jinuk.toy.util.domainhelper.BaseDomain

data class Follow(
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

    override fun hashCode(): Int {
        return _id?.hashCode() ?: 0
    }

    companion object {
        fun create(followRelation: FollowRelation) =
            with(followRelation) {
                Follow(
                    followerUserId = followerUserId,
                    followingUserId = followingUserId,
                )
            }
    }
}

internal fun FollowEntity.toModel() =
    Follow(
        _id = id,
        followerUserId = followerUserId,
        followingUserId = followingUserId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun Follow.toEntity() =
    FollowEntity(
        id = _id,
        followerUserId = followerUserId,
        followingUserId = followingUserId,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
