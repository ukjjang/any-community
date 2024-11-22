package com.anycommunity.domain.comment

data class CommentCreateInfo(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long? = null,
    val content: String,
)
