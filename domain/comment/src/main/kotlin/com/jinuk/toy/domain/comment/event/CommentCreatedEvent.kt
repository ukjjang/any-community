package com.jinuk.toy.domain.comment.event

import com.jinuk.toy.domain.comment.Comment

data class CommentCreatedEvent(
    val id: Long,
    val postId: Long,
) {
    companion object {
        fun of(comment: Comment) = CommentCreatedEvent(comment.id, comment.postId)
    }
}
