package com.anycommunity.domain.like.event

import com.anycommunity.domain.like.Like
import com.anycommunity.domain.like.LikeTarget

data class LikeAddedEvent(
    val userId: Long,
    val likeTarget: LikeTarget,
) {
    companion object {
        fun of(like: Like) = with(like) {
            LikeAddedEvent(userId, LikeTarget.from(like.targetType, like.id))
        }
    }
}
