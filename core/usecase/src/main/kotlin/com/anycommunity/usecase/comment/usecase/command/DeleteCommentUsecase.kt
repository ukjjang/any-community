package com.anycommunity.usecase.comment.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.usecase.comment.port.command.model.DeleteCommentCommand
import com.anycommunity.usecase.post.port.command.model.UpdatePostCommentCountCommand
import com.anycommunity.usecase.post.usecase.command.internal.UpdatePostCommentCountUseCase

@Service
class DeleteCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val likeCommandService: LikeCommandService,
    private val updatePostCommentCountUseCase: UpdatePostCommentCountUseCase,
) {
    @Transactional
    operator fun invoke(command: DeleteCommentCommand) {
        commentCommandService.delete(command.commentId, command.userId, command.postId)
        likeCommandService.delete(LikeTarget.from(LikeType.COMMENT, command.commentId))

        updatePostCommentCount(command.postId)
    }

    private fun updatePostCommentCount(postId: Long) {
        val command = UpdatePostCommentCountCommand(
            postId = postId,
            countOperation = CountOperation.DECREMENT,
        )
        updatePostCommentCountUseCase(command)
    }
}
