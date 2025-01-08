package com.anycommunity.usecase.follow.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.follow.port.command.model.CreateFollowCommand
import com.anycommunity.usecase.follow.port.command.model.UnFollowCommand
import com.anycommunity.usecase.follow.usecase.command.CreateFollowUseCase
import com.anycommunity.usecase.follow.usecase.command.UnFollowUseCase

sealed interface FollowCommandPort {
    infix fun execute(command: CreateFollowCommand)

    infix fun execute(command: UnFollowCommand)
}

@Service
internal class FollowCommandPortImpl(
    private val createFollowUseCase: CreateFollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
) : FollowCommandPort {
    override fun execute(command: CreateFollowCommand) = createFollowUseCase(command)

    override fun execute(command: UnFollowCommand) = unFollowUseCase(command)
}
