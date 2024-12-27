package com.anycommunity.usecase.user_feed.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user_feed.command.usecase.CreateUserFeedCommand
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedCreateUsecase
import com.anycommunity.usecase.user_feed.command.usecase.DeleteUserFeedCommandByPostDelete
import com.anycommunity.usecase.user_feed.command.usecase.DeleteUserFeedCommandByUnFollow
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteUsecase

sealed interface UserFeedCommandBus {
    infix fun execute(command: DeleteUserFeedCommandByPostDelete)
    infix fun execute(command: DeleteUserFeedCommandByUnFollow)
    infix fun execute(command: CreateUserFeedCommand)
}

@Service
internal class UserFeedCommandBusImpl(
    private val userFeedDeleteUsecase: UserFeedDeleteUsecase,
    private val userFeedCreateUsecase: UserFeedCreateUsecase,
) : UserFeedCommandBus {
    override fun execute(command: DeleteUserFeedCommandByPostDelete) = userFeedDeleteUsecase(command)
    override fun execute(command: DeleteUserFeedCommandByUnFollow) = userFeedDeleteUsecase(command)
    override fun execute(command: CreateUserFeedCommand) = userFeedCreateUsecase(command)
}
