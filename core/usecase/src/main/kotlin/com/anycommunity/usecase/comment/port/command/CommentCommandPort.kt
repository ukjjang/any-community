package com.anycommunity.usecase.comment.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.comment.port.command.model.CreateCommentCommand
import com.anycommunity.usecase.comment.port.command.model.DeleteCommentCommand
import com.anycommunity.usecase.comment.port.command.model.UpdateCommentCommand
import com.anycommunity.usecase.comment.usecase.command.CreateCommentUsecase
import com.anycommunity.usecase.comment.usecase.command.DeleteCommentUsecase
import com.anycommunity.usecase.comment.usecase.command.UpdateCommentUsecase

sealed interface CommentCommandPort {
    infix fun execute(command: CreateCommentCommand)

    infix fun execute(command: DeleteCommentCommand)

    infix fun execute(command: UpdateCommentCommand)
}

@Service
internal class CommentCommandPortImpl(
    private val createCommentUseCase: CreateCommentUsecase,
    private val deleteCommentUseCase: DeleteCommentUsecase,
    private val updateCommentUsecase: UpdateCommentUsecase,
) : CommentCommandPort {
    override fun execute(command: CreateCommentCommand) = createCommentUseCase(command)

    override fun execute(command: DeleteCommentCommand) = deleteCommentUseCase(command)

    override fun execute(command: UpdateCommentCommand) = updateCommentUsecase(command)
}
