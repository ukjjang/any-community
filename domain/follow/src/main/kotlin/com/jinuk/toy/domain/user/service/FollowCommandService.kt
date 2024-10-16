package com.jinuk.toy.domain.user.service

import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.domain.user.jpa.FollowRepository
import org.springframework.stereotype.Service

@Service
class FollowCommandService(
    private val followQueryService: FollowQueryService,
    private val followRepository: FollowRepository,
) {
    fun create(followRelation: FollowRelation) =
        require(!followQueryService.existsByFollowRelation(followRelation)) {
            "이미 팔로우 관계입니다."
        }.also {
            followRepository.save(Follow.create(followRelation))
        }

    fun delete(followRelation: FollowRelation) =
        followQueryService.getByFollowRelation(followRelation).let {
            followRepository.delete(it)
        }
}
