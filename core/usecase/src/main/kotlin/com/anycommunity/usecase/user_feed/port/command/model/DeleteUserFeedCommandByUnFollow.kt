package com.anycommunity.usecase.user_feed.port.command.model

import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.UnFollowEvent

data class DeleteUserFeedCommandByUnFollow(val followRelation: FollowRelation) {
    companion object {
        fun from(event: UnFollowEvent) = DeleteUserFeedCommandByUnFollow(event.followRelation)
    }
}
