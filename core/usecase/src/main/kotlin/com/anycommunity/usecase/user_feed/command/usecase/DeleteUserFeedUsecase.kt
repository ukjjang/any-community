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
    operator fun invoke(command: DeleteUserFeedCommandByPostDelete) =
        userFeedCommandService.deleteByPostId(command.postId)

    @Transactional
    operator fun invoke(command: DeleteUserFeedCommandByUnFollow) = with(command.followRelation) {
        userFeedCommandService.deleteByUserIdAndPostAuthorId(followerUserId, followingUserId)
    }
}

data class DeleteUserFeedCommandByPostDelete(val postId: Long) {
    companion object {
        fun from(event: PostDeletedEvent) = DeleteUserFeedCommandByPostDelete(event.id)
    }
}

data class DeleteUserFeedCommandByUnFollow(val followRelation: FollowRelation) {
    companion object {
        fun from(event: UnFollowEvent) = DeleteUserFeedCommandByUnFollow(event.followRelation)
    }
}
