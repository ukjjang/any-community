package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.post.service.PostCommandService

@Service
class DeleteCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val postCommandService: PostCommandService,
) {
    operator fun invoke(command: DeleteCommentCommand) {
        commentCommandService.delete(command.commentId, command.userId, command.postId)
        postCommandService.decreaseCommentCount(command.postId)
    }
}

data class DeleteCommentCommand(
    val userId: Long,
    val postId: Long,
    val commentId: Long,
)
