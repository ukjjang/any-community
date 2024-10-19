package com.jinuk.toy.applicaiton.comment.command

import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentCommand
import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentUsecase
import com.jinuk.toy.applicaiton.comment.command.usecase.DeleteCommentCommand
import com.jinuk.toy.applicaiton.comment.command.usecase.DeleteCommentUsecase
import com.jinuk.toy.applicaiton.comment.command.usecase.UpdateCommentCommand
import com.jinuk.toy.applicaiton.comment.command.usecase.UpdateCommentUsecase
import org.springframework.stereotype.Service

sealed interface CommentCommandBus {
    infix fun execute(command: CreateCommentCommand)

    infix fun execute(command: DeleteCommentCommand)

    infix fun execute(command: UpdateCommentCommand)
}

@Service
internal class CommentCommandBusImpl(
    private val createCommentUseCase: CreateCommentUsecase,
    private val deleteCommentUseCase: DeleteCommentUsecase,
    private val updateCommentUsecase: UpdateCommentUsecase,
) : CommentCommandBus {
    override fun execute(command: CreateCommentCommand) = createCommentUseCase(command)

    override fun execute(command: DeleteCommentCommand) = deleteCommentUseCase(command)

    override fun execute(command: UpdateCommentCommand) = updateCommentUsecase(command)
}
