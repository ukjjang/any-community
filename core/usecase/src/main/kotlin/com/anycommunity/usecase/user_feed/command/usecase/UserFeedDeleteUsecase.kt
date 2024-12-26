package com.anycommunity.usecase.user_feed.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.user_feed.service.UserFeedCommandService

@Service
class UserFeedDeleteUsecase(
    private val userFeedCommandService: UserFeedCommandService,
) {
    @Transactional
    operator fun invoke(command: UserFeedDeleteCommand) = userFeedCommandService.deleteByPostId(command.postId)
}

data class UserFeedDeleteCommand(val postId: Long)
