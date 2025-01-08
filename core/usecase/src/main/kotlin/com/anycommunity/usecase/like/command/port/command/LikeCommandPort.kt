package com.anycommunity.usecase.like.command.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.like.command.port.command.model.AddLikeCommand
import com.anycommunity.usecase.like.command.port.command.model.CancelLikeCommand
import com.anycommunity.usecase.like.command.usecase.command.AddLikeUsecase
import com.anycommunity.usecase.like.command.usecase.command.CancelLikeUsecase

sealed interface LikeCommandPort {
    infix fun execute(command: AddLikeCommand)

    infix fun execute(command: CancelLikeCommand)
}

@Service
internal class LikeCommandPortImpl(
    private val addLikeUseCase: AddLikeUsecase,
    private val cancelLikeUsecase: CancelLikeUsecase,
) : LikeCommandPort {
    override fun execute(command: AddLikeCommand) = addLikeUseCase(command)

    override fun execute(command: CancelLikeCommand) = cancelLikeUsecase(command)
}
