package com.anycommunity.usecase.user_feed.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user_feed.port.command.model.CreateUserFeedCommand
import com.anycommunity.usecase.user_feed.port.command.model.DeleteUserFeedCommandByPostDelete
import com.anycommunity.usecase.user_feed.port.command.model.DeleteUserFeedCommandByUnFollow
import com.anycommunity.usecase.user_feed.usecase.command.CreateUserFeedUsecase
import com.anycommunity.usecase.user_feed.usecase.command.DeleteUserFeedUsecase

sealed interface UserFeedCommandPort {
    infix fun execute(command: DeleteUserFeedCommandByPostDelete)
    infix fun execute(command: DeleteUserFeedCommandByUnFollow)
    infix fun execute(command: CreateUserFeedCommand)
}

@Service
internal class UserFeedCommandPortImpl(
    private val deleteUserFeedUsecase: DeleteUserFeedUsecase,
    private val createUserFeedUsecase: CreateUserFeedUsecase,
) : UserFeedCommandPort {
    override fun execute(command: DeleteUserFeedCommandByPostDelete) = deleteUserFeedUsecase(command)
    override fun execute(command: DeleteUserFeedCommandByUnFollow) = deleteUserFeedUsecase(command)
    override fun execute(command: CreateUserFeedCommand) = createUserFeedUsecase(command)
}
