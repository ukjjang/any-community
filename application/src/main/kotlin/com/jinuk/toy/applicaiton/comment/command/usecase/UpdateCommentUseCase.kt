package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.domain.comment.CommentUpdateInfo
import com.jinuk.toy.domain.comment.service.CommentCommandService

@Service
class UpdateCommentUsecase(
    private val commentCommandService: CommentCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdateCommentCommand) {
        commentCommandService.update(command.toInfo())
    }
}

data class UpdateCommentCommand(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val content: String,
) {
    fun toInfo() = CommentUpdateInfo(
        id = id,
        userId = userId,
        postId = postId,
        content = content,
    )
}
