package com.jinuk.toy.applicaiton.like.command

import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeCommand
import com.jinuk.toy.applicaiton.like.command.usecase.AddLikeUseCase
import org.springframework.stereotype.Service

sealed interface LikeCommandBus {
    infix fun execute(command: AddLikeCommand)
}

@Service
internal class LikeCommandBusImpl(
    private val addLikeUseCase: AddLikeUseCase,
) : LikeCommandBus {

    override fun execute(command: AddLikeCommand) = addLikeUseCase(command)
}
