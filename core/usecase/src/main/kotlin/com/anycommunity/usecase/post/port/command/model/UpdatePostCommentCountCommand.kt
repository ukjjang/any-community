package com.anycommunity.usecase.post.port.command.model

import com.anycommunity.definition.global.CountOperation

data class UpdatePostCommentCountCommand(
    val postId: Long,
    val countOperation: CountOperation,
)
