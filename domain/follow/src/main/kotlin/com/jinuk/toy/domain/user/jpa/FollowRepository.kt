package com.jinuk.toy.domain.user.jpa

import org.springframework.stereotype.Repository
import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.domain.user.toEntity
import com.jinuk.toy.domain.user.toModel
import com.jinuk.toy.infra.rdb.follow.jpa.FollowEntityRepository

@Repository
class FollowRepository(
    private val followEntityRepository: FollowEntityRepository,
) {
    fun save(follow: Follow) = followEntityRepository.save(follow.toEntity()).toModel()

    fun delete(follow: Follow) = followEntityRepository.delete(follow.toEntity())

    fun findByFollowerUserIdAndFollowingUserId(
        followerUserId: Long,
        followingUserId: Long,
    ) = followEntityRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)?.toModel()

    fun existsByFollowerUserIdAndFollowingUserId(
        followerUserId: Long,
        followingUserId: Long,
    ) = followEntityRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
}
