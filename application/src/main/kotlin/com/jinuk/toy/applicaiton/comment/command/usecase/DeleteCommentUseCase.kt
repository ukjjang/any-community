package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.constant.like.LikeType
import com.jinuk.toy.domain.comment.event.CommentDeletedEvent
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeCommandService
import com.jinuk.toy.infra.kafka.model.KafkaTopic
import com.jinuk.toy.infra.kafka.service.KafkaProducer

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
