package com.anycommunity.infra.mysql.follow.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.infra.mysql.follow.entity.FollowEntity

interface FollowEntityRepository : JpaRepository<FollowEntity, Long> {
    fun findByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long): FollowEntity?

    fun existsByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long): Boolean

    fun findByFollowerUserId(followerUserId: Long, pageable: Pageable): Page<FollowEntity>

    fun findByFollowingUserId(followingUserId: Long, pageable: Pageable): Page<FollowEntity>
}