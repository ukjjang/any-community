package com.jinuk.toy.applicaiton.like.command

import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeCommand
import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeUseCase
import com.jinuk.toy.applicaiton.like.command.usecase.CancelLikeCommand
import com.jinuk.toy.applicaiton.like.command.usecase.CancelLikeUsecase
import org.springframework.stereotype.Service

sealed interface LikeCommandBus {
    infix fun execute(command: AddLikeCommand)
    infix fun execute(command: CancelLikeCommand)
}

@Service
internal class LikeCommandBusImpl(
    private val addLikeUseCase: AddLikeUseCase,
    private val cancelLikeUsecase: CancelLikeUsecase
) : LikeCommandBus {

    override fun execute(command: AddLikeCommand) = addLikeUseCase(command)
    override fun execute(command: CancelLikeCommand) = cancelLikeUsecase(command)
}
