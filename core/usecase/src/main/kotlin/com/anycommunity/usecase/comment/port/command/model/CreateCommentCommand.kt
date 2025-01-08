package com.anycommunity.usecase.comment.port.command.model

import com.anycommunity.domain.comment.CommentCreateInfo

data class CreateCommentCommand(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long?,
    val content: String,
) {
    fun toInfo() = CommentCreateInfo(
        userId = userId,
        postId = postId,
        parentCommentId = parentCommentId,
        content = content,
    )
}
