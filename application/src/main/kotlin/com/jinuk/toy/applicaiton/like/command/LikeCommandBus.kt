package com.jinuk.toy.applicaiton.like.command

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeCommand
import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeUseCase
import com.jinuk.toy.applicaiton.like.command.usecase.CancelLikeCommand
import com.jinuk.toy.applicaiton.like.command.usecase.CancelLikeUsecase
import com.jinuk.toy.applicaiton.like.command.usecase.UpdateLikeCountCommand
import com.jinuk.toy.applicaiton.like.command.usecase.UpdateLikeCountUsecase

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
