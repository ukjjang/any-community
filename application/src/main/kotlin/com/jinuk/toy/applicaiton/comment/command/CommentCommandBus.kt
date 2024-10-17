package com.jinuk.toy.applicaiton.comment.command

import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentCommand
import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentUsecase
import org.springframework.stereotype.Service

sealed interface CommentCommandBus {
    infix fun execute(command: CreateCommentCommand)
}

@Service
internal class CommentCommandBusImpl(
    private val createCommentUseCase: CreateCommentUsecase,
) : CommentCommandBus {

    override fun execute(command: CreateCommentCommand) = createCommentUseCase(command)
}
