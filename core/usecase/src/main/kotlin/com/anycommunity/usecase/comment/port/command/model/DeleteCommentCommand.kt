package com.anycommunity.usecase.comment.port.command.model

data class DeleteCommentCommand(
    val userId: Long,
    val postId: Long,
    val commentId: Long,
)
