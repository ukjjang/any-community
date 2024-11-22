package com.anycommunity.domain.comment.event

data class CommentDeletedEvent(
    val id: Long,
    val postId: Long,
)
