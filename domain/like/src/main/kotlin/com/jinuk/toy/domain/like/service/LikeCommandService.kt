package com.jinuk.toy.domain.like.service

import org.springframework.stereotype.Service
import com.jinuk.toy.common.define.like.LikeType
import com.jinuk.toy.domain.like.Like
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.jpa.LikeRepository

@Service
class LikeCommandService(
    private val likeQueryService: LikeQueryService,
    private val likeRepository: LikeRepository,
) {
    fun add(
        userId: Long,
        likeTarget: LikeTarget,
    ) = require(!likeQueryService.existsByUserIdAndTarget(userId, likeTarget)) {
        "이미 좋아요 했습니다."
    }.let {
        likeRepository.save(Like.create(userId, likeTarget))
    }

    fun delete(
        userId: Long,
        likeTarget: LikeTarget,
    ) = likeQueryService.getByUserIdAndTarget(userId, likeTarget).let {
        likeRepository.delete(it)
    }

    fun delete(target: LikeTarget) =
        likeQueryService.findByTarget(target).let {
            likeRepository.deleteAll(it)
        }

    fun delete(
        likeType: LikeType,
        ids: List<Long>,
    ) = likeQueryService.findByTargetTypeAndTargetIdIn(likeType, ids).let {
        likeRepository.deleteAll(it)
    }
}
