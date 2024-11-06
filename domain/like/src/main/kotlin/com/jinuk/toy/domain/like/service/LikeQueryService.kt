package com.jinuk.toy.domain.like.service

import org.springframework.stereotype.Service
import com.jinuk.toy.constant.like.LikeType
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.jpa.LikeRepository

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

    fun findByTarget(target: LikeTarget) = likeRepository.findByTargetTypeAndTargetId(target)

    fun findByUserIdAndTargetTypeAndTargetIdIn(
        userId: Long,
        targetType: LikeType,
        targetId: List<String>,
    ) = likeRepository.findByUserIdAndTargetTypeAndTargetIdIn(userId, targetType, targetId.toList())

    fun findByTargetTypeAndTargetIdIn(
        targetType: LikeType,
        targetId: List<Long>,
    ) = likeRepository.findByTargetTypeAndTargetIdIn(targetType, targetId.toList())
}
