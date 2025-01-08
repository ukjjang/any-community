package com.anycommunity.usecase.user_feed.port.command.model

import com.anycommunity.domain.post.event.PostCreatedEvent

data class CreateUserFeedCommand(
    val postId: Long,
    val userId: Long,
) {
    companion object {
        fun from(event: PostCreatedEvent) = CreateUserFeedCommand(event.id, event.userId)
    }
}
