package com.jinuk.toy.domain.comment

data class CommentCreateInfo(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long? = null,
    val content: String,
)
