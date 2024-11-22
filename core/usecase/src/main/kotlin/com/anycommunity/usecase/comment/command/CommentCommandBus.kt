package com.anycommunity.usecase.comment.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.comment.command.usecase.CreateCommentCommand
import com.anycommunity.usecase.comment.command.usecase.CreateCommentUsecase
import com.anycommunity.usecase.comment.command.usecase.DeleteCommentCommand
import com.anycommunity.usecase.comment.command.usecase.DeleteCommentUsecase
import com.anycommunity.usecase.comment.command.usecase.UpdateCommentCommand
import com.anycommunity.usecase.comment.command.usecase.UpdateCommentUsecase

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
