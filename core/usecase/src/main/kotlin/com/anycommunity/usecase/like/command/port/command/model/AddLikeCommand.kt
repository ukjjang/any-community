package com.anycommunity.usecase.like.command.port.command.model

import com.anycommunity.domain.like.LikeTarget

data class AddLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
