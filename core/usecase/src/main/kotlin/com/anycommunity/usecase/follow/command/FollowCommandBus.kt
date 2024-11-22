package com.anycommunity.usecase.follow.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.follow.command.usecase.CreateFollowCommand
import com.anycommunity.usecase.follow.command.usecase.CreateFollowUseCase
import com.anycommunity.usecase.follow.command.usecase.UnFollowCommand
import com.anycommunity.usecase.follow.command.usecase.UnFollowUseCase

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
