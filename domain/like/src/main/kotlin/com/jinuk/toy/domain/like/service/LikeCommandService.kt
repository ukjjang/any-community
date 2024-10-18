package com.jinuk.toy.domain.like.service

import com.jinuk.toy.domain.like.Like
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.jpa.LikeRepository
import org.springframework.stereotype.Service

@Service
class LikeCommandService(
    private val likeQueryService: LikeQueryService,
    private val likeRepository: LikeRepository,
) {

    fun add(userId: Long, likeTarget: LikeTarget) =
        require(!likeQueryService.existsByUserIdAndTargetTypeAndTargetId(userId, likeTarget)) {
            "이미 좋아요 했습니다."
        }.let {
            likeRepository.save(Like.create(userId, likeTarget))
        }

    fun delete(userId: Long, likeTarget: LikeTarget) =
        likeQueryService.getByUserIdAndTargetTypeAndTargetId(userId, likeTarget).let {
            likeRepository.delete(it)
        }
}
