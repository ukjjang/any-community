package com.anycommunity.domain.follow.service

import org.springframework.stereotype.Service
import com.anycommunity.domain.follow.Follow
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.jpa.FollowRepository

@Service
class FollowCommandService(
    private val followQueryService: FollowQueryService,
    private val followRepository: FollowRepository,
) {
    fun create(followRelation: FollowRelation): Follow {
        require(!followQueryService.existsByFollowRelation(followRelation)) {
            "이미 팔로우 관계입니다."
        }
        return followRepository.save(Follow.create(followRelation))
    }

    fun delete(followRelation: FollowRelation) = followQueryService.getByFollowRelation(followRelation).let {
        followRepository.delete(it)
    }
}
