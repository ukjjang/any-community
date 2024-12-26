package com.anycommunity.usecase.like.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.like.command.usecase.AddLikeCommand
import com.anycommunity.usecase.like.command.usecase.AddLikeUseCase
import com.anycommunity.usecase.like.command.usecase.CancelLikeCommand
import com.anycommunity.usecase.like.command.usecase.CancelLikeUsecase
import com.anycommunity.usecase.like.command.usecase.internal.UpdateLikeCountCommand
import com.anycommunity.usecase.like.command.usecase.internal.UpdateLikeCountUsecase

sealed interface LikeCommandBus {
    infix fun execute(command: AddLikeCommand)

    infix fun execute(command: CancelLikeCommand)

    infix fun execute(command: UpdateLikeCountCommand)
}

@Service
internal class LikeCommandBusImpl(
    private val addLikeUseCase: AddLikeUseCase,
    private val cancelLikeUsecase: CancelLikeUsecase,
    private val updateLikeCountUsecase: UpdateLikeCountUsecase,
) : LikeCommandBus {
    override fun execute(command: AddLikeCommand) = addLikeUseCase(command)

    override fun execute(command: CancelLikeCommand) = cancelLikeUsecase(command)

    override fun execute(command: UpdateLikeCountCommand) = updateLikeCountUsecase(command)
}
