package com.jinuk.toy.domain.user.service

import com.jinuk.toy.domain.user.FollowRelation
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

    fun existsByFollowRelation(followRelation: FollowRelation) = with(followRelation) {
        followRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
    }

    fun getByFollowRelation(followRelation: FollowRelation) = with(followRelation) {
        followRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
            ?: throw NoSuchElementException("존재하지 않는 팔로우 관계입니다.")
    }

    fun findByFollowRelation(followRelation: FollowRelation) = with(followRelation) {
        followRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
    }
}
