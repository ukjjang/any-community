package com.jinuk.toy.applicaiton.follow.command

import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowCommand
import com.jinuk.toy.applicaiton.follow.command.usecase.CreateFollowUseCase
import com.jinuk.toy.applicaiton.follow.command.usecase.UnFollowCommand
import com.jinuk.toy.applicaiton.follow.command.usecase.UnFollowUseCase
import org.springframework.stereotype.Service

sealed interface FollowCommandBus {
    infix fun execute(command: CreateFollowCommand)
    infix fun execute(command: UnFollowCommand)
}

@Service
internal class FollowCommandBusImpl(
    private val createFollowUseCase: CreateFollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
) : FollowCommandBus {

    override fun execute(command: CreateFollowCommand) = createFollowUseCase(command)
    override fun execute(command: UnFollowCommand) = unFollowUseCase(command)
}
