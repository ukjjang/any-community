package com.jinuk.toy.applicaiton.comment.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.define.global.kafka.KafkaTopic
import com.jinuk.toy.domain.comment.Comment
import com.jinuk.toy.domain.comment.event.CommentCreatedEvent
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.infra.kafka.service.KafkaProducer

@Service
class CreateCommentUsecase(
    private val commentCommandService: CommentCommandService,
    private val kafkaProducer: KafkaProducer,
) {
    @Transactional
    operator fun invoke(command: CreateCommentCommand) {
        val comment = commentCommandService.save(command.toComment())
        kafkaProducer.send(
            topic = KafkaTopic.Comment.CREATE,
            payload = CommentCreatedEvent.of(comment),
        )
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
