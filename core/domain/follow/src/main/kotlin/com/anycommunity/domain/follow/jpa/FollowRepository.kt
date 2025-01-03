package com.anycommunity.domain.follow.jpa

import org.springframework.stereotype.Repository
import com.anycommunity.domain.follow.Follow
import com.anycommunity.domain.follow.toEntity
import com.anycommunity.domain.follow.toModel
import com.anycommunity.infra.mysql.follow.jpa.FollowEntityRepository

@Repository
class FollowRepository(
    private val followEntityRepository: FollowEntityRepository,
) {
    fun save(follow: Follow) = followEntityRepository.save(follow.toEntity()).toModel()

    fun delete(follow: Follow) = followEntityRepository.delete(follow.toEntity())

    fun findByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followEntityRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)?.toModel()

    fun existsByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followEntityRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)

    fun findByFollowingUserId(followingUserId: Long) =
        followEntityRepository.findByFollowingUserId(followingUserId).map { it.toModel() }
}
