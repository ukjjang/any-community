package com.jinuk.toy.applicaiton.comment.command.usecase

import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.service.CommentCommandService
import org.springframework.stereotype.Service

@Service
class CreateCommentUsecase(
    private val commentCommandService: CommentCommandService,
) {
    operator fun invoke(command: CreateCommentCommand) {
        commentCommandService.save(command.toComment())
    }
}

data class CreateCommentCommand(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long?,
    val content: String,
)

private fun CreateCommentCommand.toComment() = Comment(
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
)
