package com.anycommunity.consumer.post

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.anycommunity.consumer.global.KafkaEventParser
import com.anycommunity.definition.global.kafka.KafkaGroupId
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.comment.event.CommentCreatedEvent
import com.anycommunity.domain.comment.event.CommentDeletedEvent
import com.anycommunity.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.anycommunity.usecase.post.command.PostCommandBus
import com.anycommunity.usecase.post.command.usecase.UpdatePostCommentCountCommand

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
    fun postCreatedEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, CommentCreatedEvent::class.java)
        postCommandBus execute UpdatePostCommentCountCommand.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Comment.DELETE],
        groupId = KafkaGroupId.Post.DECREASE_COMMENT_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun postDeletedEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, CommentDeletedEvent::class.java)
        postCommandBus execute UpdatePostCommentCountCommand.from(event)
    }
}
