package com.jinuk.toy.domain.like.event

import com.jinuk.toy.domain.like.LikeTarget

data class LikeCanceledEvent(
    val userId: Long,
    val likeTarget: LikeTarget,
)
