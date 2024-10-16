package com.jinuk.toy.applicaiton.follow.command

import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowCommand
import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowUseCase
import org.springframework.stereotype.Service

sealed interface FollowCommandBus {
    infix fun execute(command: CreateFollowCommand)
}

@Service
internal class FollowCommandBusImpl(
    private val createFollowUseCase: CreateFollowUseCase,
) : FollowCommandBus {

    override fun execute(command: CreateFollowCommand) = createFollowUseCase(command)
}
