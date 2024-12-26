package com.anycommunity.domain.follow.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.anycommunity.definition.follow.FollowSearchSortType
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.jpa.FollowJdslRepository
import com.anycommunity.domain.follow.jpa.FollowRepository

@Service
class FollowQueryService(
    private val followRepository: FollowRepository,
    private val followJdslRepository: FollowJdslRepository,
) {
    fun existsByFollowRelation(followRelation: FollowRelation) = with(followRelation) {
        followRepository.existsByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
    }

    fun getByFollowRelation(followRelation: FollowRelation) = with(followRelation) {
        followRepository.findByFollowerUserIdAndFollowingUserId(followerUserId, followingUserId)
            ?: throw NoSuchElementException("존재하지 않는 팔로우 관계입니다.")
    }

    fun findByFollowingUserId(followingUserId: Long) = followRepository.findByFollowingUserId(followingUserId)

    fun search(
        followingUserId: Long? = null,
        followerUserId: Long? = null,
        pageable: Pageable,
        sortType: FollowSearchSortType,
    ) = followJdslRepository.search(
        followingUserId = followingUserId,
        followerUserId = followerUserId,
        pageable = pageable,
        sortType = sortType,
    )
}
