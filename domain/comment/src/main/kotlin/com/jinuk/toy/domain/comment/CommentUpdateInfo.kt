package com.jinuk.toy.domain.comment

data class CommentUpdateInfo(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val content: String,
)
