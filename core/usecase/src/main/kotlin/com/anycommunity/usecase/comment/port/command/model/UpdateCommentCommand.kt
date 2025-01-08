package com.anycommunity.usecase.comment.port.command.model

import com.anycommunity.domain.comment.CommentUpdateInfo

data class UpdateCommentCommand(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val content: String,
) {
    fun toInfo() = CommentUpdateInfo(
        id = id,
        userId = userId,
        postId = postId,
        content = content,
    )
}
