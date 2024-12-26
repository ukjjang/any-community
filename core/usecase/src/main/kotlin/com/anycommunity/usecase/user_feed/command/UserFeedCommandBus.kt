package com.anycommunity.usecase.user_feed.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedCreateCommand
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedCreateUsecase
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteCommandByPostDelete
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteCommandByUnFollow
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteUsecase

sealed interface UserFeedCommandBus {
    infix fun execute(command: UserFeedDeleteCommandByPostDelete)
    infix fun execute(command: UserFeedDeleteCommandByUnFollow)
    infix fun execute(command: UserFeedCreateCommand)
}

@Service
internal class UserFeedCommandBusImpl(
    private val userFeedDeleteUsecase: UserFeedDeleteUsecase,
    private val userFeedCreateUsecase: UserFeedCreateUsecase,
) : UserFeedCommandBus {
    override fun execute(command: UserFeedDeleteCommandByPostDelete) = userFeedDeleteUsecase(command)
    override fun execute(command: UserFeedDeleteCommandByUnFollow) = userFeedDeleteUsecase(command)
    override fun execute(command: UserFeedCreateCommand) = userFeedCreateUsecase(command)
}
