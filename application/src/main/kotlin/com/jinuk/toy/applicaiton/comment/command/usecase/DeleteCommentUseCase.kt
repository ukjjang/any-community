package com.jinuk.toy.applicaiton.comment.command.usecase

import com.jinuk.toy.domain.comment.service.CommentCommandService
import org.springframework.stereotype.Service

@Service
class DeleteCommentUsecase(
    private val commentCommandService: CommentCommandService,
) {
    operator fun invoke(command: DeleteCommentCommand) =
        commentCommandService.delete(command.commentId, command.userId, command.postId)
}

data class DeleteCommentCommand(
    val userId: Long,
    val postId: Long,
    val commentId: Long,
)
