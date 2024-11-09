package com.jinuk.toy.infra.rdb.follow.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.follow.entity.FollowEntity

interface FollowEntityRepository : JpaRepository<FollowEntity, Long> {
    fun findByFollowerUserIdAndFollowingUserId(
        followerUserId: Long,
        followingUserId: Long,
    ): FollowEntity?

    fun existsByFollowerUserIdAndFollowingUserId(
        followerUserId: Long,
        followingUserId: Long,
    ): Boolean

    fun findByFollowerUserIdOrderByIdDesc(
        followerUserId: Long,
        pageable: Pageable,
    ): Page<FollowEntity>
}
