package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.comment.service.CommentQueryService

@Service
class UpdateCommentUsecase(
    private val commentQueryService: CommentQueryService,
    private val commentCommandService: CommentCommandService,
) {
    operator fun invoke(command: UpdateCommentCommand) {
        val comment = commentQueryService.getById(command.id)
        val updateComment = comment.update(command)
        commentCommandService.save(updateComment)
    }
}

data class UpdateCommentCommand(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val content: String,
)

fun Comment.update(command: UpdateCommentCommand): Comment {
    require(this.userId == command.userId) { "작성자만 게시글을 수정할 수 있습니다." }
    require(this.postId == command.postId) { "해당 댓글은 지정된 게시글에 속하지 않습니다." }
    return this.copy(
        userId = command.userId,
        postId = command.postId,
        parentCommentId = parentCommentId,
        content = command.content,
    )
}
