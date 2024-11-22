package com.anycommunity.usecase.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.event.CommentDeletedEvent
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.infra.kafka.service.KafkaProducer

@Service
class DeleteCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val kafkaProducer: KafkaProducer,
    private val likeCommandService: LikeCommandService,
) {
    @Transactional
    operator fun invoke(command: DeleteCommentCommand) {
        commentCommandService.delete(command.commentId, command.userId, command.postId)
        kafkaProducer.send(
            topic = KafkaTopic.Comment.CREATE,
            payload = CommentDeletedEvent(id = command.commentId, postId = command.postId),
        )
        likeCommandService.delete(LikeTarget.from(LikeType.COMMENT, command.commentId))
    }
}

data class DeleteCommentCommand(
    val userId: Long,
    val postId: Long,
    val commentId: Long,
)
