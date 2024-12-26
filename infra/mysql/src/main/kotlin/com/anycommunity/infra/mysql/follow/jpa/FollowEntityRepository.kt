package com.anycommunity.infra.mysql.follow.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.infra.mysql.follow.entity.FollowEntity

interface FollowEntityRepository : JpaRepository<FollowEntity, Long> {
    fun findByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long): FollowEntity?

    fun existsByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long): Boolean

    fun findByFollowingUserId(followingUserId: Long): List<FollowEntity>
}
