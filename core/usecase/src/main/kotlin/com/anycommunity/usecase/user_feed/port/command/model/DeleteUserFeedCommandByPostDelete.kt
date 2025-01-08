package com.anycommunity.usecase.user_feed.port.command.model

import com.anycommunity.domain.post.event.PostDeletedEvent

data class DeleteUserFeedCommandByPostDelete(val postId: Long) {
    companion object {
        fun from(event: PostDeletedEvent) = DeleteUserFeedCommandByPostDelete(event.id)
    }
}
