package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.post.service.PostCommandService

@Service
class CreateCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val postCommandService: PostCommandService,
) {
    operator fun invoke(command: CreateCommentCommand) {
        commentCommandService.save(command.toComment())
        postCommandService.increaseCommentCount(command.postId)
    }
}

data class CreateCommentCommand(
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long?,
    val content: String,
)

private fun CreateCommentCommand.toComment() =
    Comment(
        userId = userId,
        postId = postId,
        parentCommentId = parentCommentId,
        content = content,
    )
