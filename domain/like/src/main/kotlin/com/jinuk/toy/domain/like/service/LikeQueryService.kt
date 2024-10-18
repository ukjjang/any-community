package com.jinuk.toy.domain.like.service

import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.jpa.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likeRepository: LikeRepository
) {
    fun existsByUserIdAndTargetTypeAndTargetId(userId: Long, target: LikeTarget) =
        likeRepository.existsByUserIdAndTargetTypeAndTargetId(userId, target)

    fun countByUserIdAndTargetTypeAndTargetId(userId: Long, target: LikeTarget) =
        likeRepository.countByUserIdAndTargetTypeAndTargetId(userId, target)
}
