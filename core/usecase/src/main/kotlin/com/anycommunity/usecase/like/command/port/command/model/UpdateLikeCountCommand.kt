package com.anycommunity.usecase.like.command.port.command.model

import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.like.LikeTarget

data class UpdateLikeCountCommand(
    val likeTarget: LikeTarget,
    val countOperation: CountOperation,
)
