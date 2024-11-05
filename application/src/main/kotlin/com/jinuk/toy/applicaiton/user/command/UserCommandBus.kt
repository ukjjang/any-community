package com.jinuk.toy.applicaiton.user.command

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.user.command.usecase.UpdateUserFollowCountCommand
import com.jinuk.toy.applicaiton.user.command.usecase.UpdateUserFollowCountUsecase

sealed interface UserCommandBus {
    infix fun execute(command: UpdateUserFollowCountCommand)
}

@Service
internal class UserCommandBusImpl(
    private val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase,
) : UserCommandBus {
    override fun execute(command: UpdateUserFollowCountCommand) = updateUserFollowCountUsecase(command)
}
