package com.anycommunity.usecase.like.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.like.command.usecase.AddLikeCommand
import com.anycommunity.usecase.like.command.usecase.AddLikeUseCase
import com.anycommunity.usecase.like.command.usecase.CancelLikeCommand
import com.anycommunity.usecase.like.command.usecase.CancelLikeUsecase

sealed interface LikeCommandBus {
    infix fun execute(command: AddLikeCommand)

    infix fun execute(command: CancelLikeCommand)
}

@Service
internal class LikeCommandBusImpl(
    private val addLikeUseCase: AddLikeUseCase,
    private val cancelLikeUsecase: CancelLikeUsecase,
) : LikeCommandBus {
    override fun execute(command: AddLikeCommand) = addLikeUseCase(command)

    override fun execute(command: CancelLikeCommand) = cancelLikeUsecase(command)
}
