package com.anycommunity.usecase.user_feed.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteCommand
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteUsecase

sealed interface UserFeedCommandBus {
    infix fun execute(command: UserFeedDeleteCommand)
}

@Service
internal class UserFeedCommandBusImpl(
    private val userFeedDeleteUsecase: UserFeedDeleteUsecase,
) : UserFeedCommandBus {
    override fun execute(command: UserFeedDeleteCommand) = userFeedDeleteUsecase(command)
}
