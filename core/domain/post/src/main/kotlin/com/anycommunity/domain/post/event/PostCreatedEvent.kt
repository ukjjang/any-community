package com.anycommunity.domain.post.event

import com.anycommunity.domain.post.Post

data class PostCreatedEvent(
    val id: Long,
    val userId: Long,
) {
    companion object {
        fun from(post: Post) = with(post) {
            PostCreatedEvent(id, userId)
        }
    }
}
