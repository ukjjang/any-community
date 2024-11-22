package com.anycommunity.usecase.user.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user.command.usecase.UpdateUserFollowCountCommand
import com.anycommunity.usecase.user.command.usecase.UpdateUserFollowCountUsecase

sealed interface UserCommandBus {
    infix fun execute(command: UpdateUserFollowCountCommand)
}

@Service
internal class UserCommandBusImpl(
    private val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase,
) : UserCommandBus {
    override fun execute(command: UpdateUserFollowCountCommand) = updateUserFollowCountUsecase(command)
}
