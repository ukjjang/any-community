package com.jinuk.toy.domain.follow.service

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.follow.FollowSearchSortType
import com.jinuk.toy.domain.follow.FollowRelation
import com.jinuk.toy.domain.follow.jpa.FollowJdslRepository
import com.jinuk.toy.domain.follow.jpa.FollowRepository

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

    fun findByFollowerUserId(followerUserId: Long, pageable: Pageable) =
        followRepository.findByFollowerUserId(followerUserId, pageable)

    fun findByFollowingUserId(followingUserId: Long, pageable: Pageable) =
        followRepository.findByFollowingUserId(followingUserId, pageable)

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
