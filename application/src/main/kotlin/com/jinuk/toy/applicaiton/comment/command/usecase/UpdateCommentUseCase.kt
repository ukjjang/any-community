package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.comment.service.CommentQueryService

@Service
class UpdateCommentUsecase(
    private val commentQueryService: CommentQueryService,
    private val commentCommandService: CommentCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdateCommentCommand) {
        val comment = commentQueryService.getById(command.id)
        validate(comment, command)
        comment.update(content = command.content)
            .let { commentCommandService.save(it) }
    }

    private fun validate(comment: Comment, command: UpdateCommentCommand) {
        require(comment.userId == command.userId) { "작성자만 게시글을 수정할 수 있습니다." }
        require(comment.postId == command.postId) { "해당 댓글은 지정된 게시글에 속하지 않습니다." }
    }
}

data class UpdateCommentCommand(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val content: String,
)
