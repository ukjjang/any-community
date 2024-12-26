package com.anycommunity.usecase.user_feed.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.UnFollowEvent
import com.anycommunity.domain.post.event.PostDeletedEvent
import com.anycommunity.domain.user_feed.service.UserFeedCommandService

@Service
class UserFeedDeleteUsecase(
    private val userFeedCommandService: UserFeedCommandService,
) {
    @Transactional
    operator fun invoke(command: UserFeedDeleteCommandByPostDelete) =
        userFeedCommandService.deleteByPostId(command.postId)

    @Transactional
    operator fun invoke(command: UserFeedDeleteCommandByUnFollow) = with(command.followRelation) {
        userFeedCommandService.deleteByUserIdAndPostAuthorId(followerUserId, followingUserId)
    }
}

data class UserFeedDeleteCommandByPostDelete(val postId: Long) {
    companion object {
        fun from(event: PostDeletedEvent) = UserFeedDeleteCommandByPostDelete(event.id)
    }
}

data class UserFeedDeleteCommandByUnFollow(val followRelation: FollowRelation) {
    companion object {
        fun from(event: UnFollowEvent) = UserFeedDeleteCommandByUnFollow(event.followRelation)
    }
}
