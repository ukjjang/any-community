package com.jinuk.toy.consumer.view.post

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.jinuk.toy.applicaiton.post.command.PostCommandBus
import com.jinuk.toy.applicaiton.post.command.usecase.UpdatePostCommentCountCommand
import com.jinuk.toy.consumer.global.KafkaEventParser
import com.jinuk.toy.domain.comment.event.CommentCreatedEvent
import com.jinuk.toy.domain.comment.event.CommentDeletedEvent
import com.jinuk.toy.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.jinuk.toy.infra.kafka.model.KafkaGroupId
import com.jinuk.toy.infra.kafka.model.KafkaTopic

@Component
class PostConsumer(
    private val kafkaEventParser: KafkaEventParser,
    private val postCommandBus: PostCommandBus,
) {
    @KafkaListener(
        topics = [KafkaTopic.Comment.CREATE],
        groupId = KafkaGroupId.Post.INCREASE_COMMENT_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentCreatedEventConsume(
        @Payload message: String,
    ) {
        val event = kafkaEventParser.parse(message, CommentCreatedEvent::class.java)
        postCommandBus execute UpdatePostCommentCountCommand.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Comment.DELETE],
        groupId = KafkaGroupId.Post.DECREASE_COMMENT_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentDeletedEventConsume(
        @Payload message: String,
    ) {
        val event = kafkaEventParser.parse(message, CommentDeletedEvent::class.java)
        postCommandBus execute UpdatePostCommentCountCommand.from(event)
    }
}
