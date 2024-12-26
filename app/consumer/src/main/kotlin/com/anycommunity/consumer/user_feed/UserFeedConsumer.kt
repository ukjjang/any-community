package com.anycommunity.consumer.user_feed

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.anycommunity.consumer.global.KafkaEventParser
import com.anycommunity.definition.global.kafka.KafkaGroupId
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.post.event.PostDeletedEvent
import com.anycommunity.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.anycommunity.usecase.user_feed.command.UserFeedCommandBus
import com.anycommunity.usecase.user_feed.command.usecase.UserFeedDeleteCommand

@Component
class UserFeedConsumer(
    private val kafkaEventParser: KafkaEventParser,
    private val userFeedCommandBus: UserFeedCommandBus,
) {
    @KafkaListener(
        topics = [KafkaTopic.Post.DELETE],
        groupId = KafkaGroupId.UserFeed.DELETE_BY_POST_DELETE,
        containerFactory = LISTENER_FACTORY,
    )
    fun postDeletedEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, PostDeletedEvent::class.java)
        userFeedCommandBus execute UserFeedDeleteCommand(event.id)
    }
}
