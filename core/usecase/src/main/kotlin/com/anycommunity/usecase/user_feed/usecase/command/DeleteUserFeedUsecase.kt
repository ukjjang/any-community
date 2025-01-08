package com.anycommunity.usecase.user_feed.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.user_feed.service.UserFeedCommandService
import com.anycommunity.usecase.user_feed.port.command.model.DeleteUserFeedCommandByPostDelete
import com.anycommunity.usecase.user_feed.port.command.model.DeleteUserFeedCommandByUnFollow

@Service
class DeleteUserFeedUsecase(
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
