package com.jinuk.toy.infra.rdb.follow.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.follow.entity.FollowEntity

interface FollowEntityRepository : JpaRepository<FollowEntity, Long> {
    fun findByFollowerUserId(followerUserId: Long): List<FollowEntity>

    fun findByFollowingUserId(followingUserId: Long): List<FollowEntity>

    fun findByFollowerUserIdAndFollowingUserId(
        followerUserId: Long,
        followingUserId: Long,
    ): FollowEntity?

    fun existsByFollowerUserIdAndFollowingUserId(
        followerUserId: Long,
        followingUserId: Long,
    ): Boolean
}
