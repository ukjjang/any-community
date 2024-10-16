package com.jinuk.toy.domain.user

import com.jinuk.toy.infra.rdb.follow.entity.FollowEntity
import com.jinuk.toy.util.domainhelper.BaseDomain
import java.time.LocalDateTime

data class Follow(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),

    val followerUserId: Long,
    val followingUserId: Long,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()

    companion object {
        fun create(followRelation: FollowRelation) = with(followRelation) {
            Follow(
                followerUserId = followerUserId,
                followingUserId = followingUserId,
            )
        }
    }
}

internal fun FollowEntity.toModel() = Follow(
    _id = id,
    followerUserId = followerUserId,
    followingUserId = followingUserId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun Follow.toEntity() = FollowEntity(
    id = _id,
    followerUserId = followerUserId,
    followingUserId = followingUserId,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
