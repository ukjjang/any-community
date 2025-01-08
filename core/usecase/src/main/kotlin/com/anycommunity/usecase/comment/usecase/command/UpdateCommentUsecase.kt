package com.anycommunity.usecase.comment.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.usecase.comment.port.command.model.UpdateCommentCommand

@Service
class UpdateCommentUsecase(
    private val commentCommandService: CommentCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdateCommentCommand) {
        commentCommandService.update(command.toInfo())
    }
}
