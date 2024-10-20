package com.jinuk.toy.domain.like.service

import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.like.jpa.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeQueryService(
    private val likeRepository: LikeRepository,
) {
    fun getByUserIdAndTarget(
        userId: Long,
        target: LikeTarget,
    ) = likeRepository.findByUserIdAndTarget(userId, target)
        ?: throw NoSuchElementException("좋아요가 존재하지 않습니다.")

    fun existsByUserIdAndTarget(
        userId: Long,
        target: LikeTarget,
    ) = likeRepository.existsByUserIdAndTarget(userId, target)

    fun countByTarget(target: LikeTarget) = likeRepository.countByTarget(target)

    fun findByTargetTypeAndTargetIdIn(
        targetType: LikeType,
        targetId: List<String>,
    ) = likeRepository.findByTargetTypeAndTargetIdIn(targetType, targetId)

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: LikeType,
        targetId: List<String>,
    ) = likeRepository.findByUserIdAndTargetTypeAndTargetIdIn(userId, targetType, targetId.toList())
}
