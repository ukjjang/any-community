package com.jinuk.toy.domain.user.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.domain.user.jpa.FollowRepository

@Service
class FollowQueryService(
    private val followRepository: FollowRepository,
) {
    fun existsByFollowRelation(followRelation: FollowRelation) =
        with(followRelation) {
            followRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
        }

    fun getByFollowRelation(followRelation: FollowRelation) =
        with(followRelation) {
            followRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
                ?: throw NoSuchElementException("존재하지 않는 팔로우 관계입니다.")
        }

    fun findByFollowerUserIdOrderByIdDesc(
        followerUserId: Long,
        pageable: Pageable,
    ) = followRepository.findByFollowerUserIdOrderByIdDesc(followerUserId, pageable)
}
