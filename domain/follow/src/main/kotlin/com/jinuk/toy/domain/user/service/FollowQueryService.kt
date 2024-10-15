package com.jinuk.toy.domain.user.service

import com.jinuk.toy.domain.user.jpa.FollowRepository
import org.springframework.stereotype.Service

@Service
class FollowQueryService(
    private val followRepository: FollowRepository,
) {
    fun findByFollowerUserId(followerUserId: Long) =
        followRepository.findByFollowerUserId(followerUserId)

    fun findByFollowingUserId(followingUserId: Long) =
        followRepository.findByFollowingUserId(followingUserId)

    fun findByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)

    fun getByFollowerUserIdAndFollowingUserId(followerUserId: Long, followingUserId: Long) =
        followRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
            ?: throw NoSuchElementException("존재하지 않는 팔로우입니다.")
}
