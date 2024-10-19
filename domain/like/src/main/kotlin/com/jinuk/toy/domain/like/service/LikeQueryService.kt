package com.jinuk.toy.domain.like.service

import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.jpa.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likeRepository: LikeRepository,
) {
    fun getByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        target: LikeTarget,
    ) = likeRepository.findByUserIdAndTargetTypeAndTargetId(userId, target)
        ?: throw NoSuchElementException("좋아요가 존재하지 않습니다.")

    fun existsByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        target: LikeTarget,
    ) = likeRepository.existsByUserIdAndTargetTypeAndTargetId(userId, target)

    fun countByUserIdAndTargetTypeAndTargetId(
        userId: Long,
        target: LikeTarget,
    ) = likeRepository.countByUserIdAndTargetTypeAndTargetId(userId, target)
}
