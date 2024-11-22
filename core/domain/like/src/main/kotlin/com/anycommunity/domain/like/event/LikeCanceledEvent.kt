package com.anycommunity.domain.like.event

import com.anycommunity.domain.like.LikeTarget

data class LikeCanceledEvent(
    val userId: Long,
    val likeTarget: LikeTarget,
)
