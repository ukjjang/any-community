package com.jinuk.toy.domain.follow.jpa

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.jinuk.toy.domain.follow.Follow
import com.jinuk.toy.domain.follow.toEntity
import com.jinuk.toy.domain.follow.toModel
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

    fun findByFollowerUserId(
        followerUserId: Long,
        pageable: Pageable,
    ) = followEntityRepository.findByFollowerUserId(followerUserId, pageable).map { it.toModel() }

    fun findByFollowingUserId(
        followingUserId: Long,
        pageable: Pageable,
    ) = followEntityRepository.findByFollowingUserId(followingUserId, pageable).map { it.toModel() }
}