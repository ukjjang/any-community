package com.anycommunity.usecase.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.event.CommentDeletedEvent
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator

@Service
class DeleteCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val likeCommandService: LikeCommandService,
    private val outboxCreator: OutboxCreator,
) {
    @Transactional
    operator fun invoke(command: DeleteCommentCommand) {
        commentCommandService.delete(command.commentId, command.userId, command.postId)
        likeCommandService.delete(LikeTarget.from(LikeType.COMMENT, command.commentId))

        outboxCreator.create(
            KafkaTopic.Comment.DELETE,
            CommentDeletedEvent(id = command.commentId, postId = command.postId),
        )
    }
}

data class DeleteCommentCommand(
    val userId: Long,
    val postId: Long,
    val commentId: Long,
)
