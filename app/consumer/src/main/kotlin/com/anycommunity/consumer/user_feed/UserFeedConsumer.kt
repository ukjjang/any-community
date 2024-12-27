package com.anycommunity.consumer.user_feed

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.anycommunity.consumer.global.KafkaEventParser
import com.anycommunity.definition.global.kafka.KafkaGroupId
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.follow.event.UnFollowEvent
import com.anycommunity.domain.post.event.PostCreatedEvent
import com.anycommunity.domain.post.event.PostDeletedEvent
import com.anycommunity.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.anycommunity.usecase.user_feed.command.UserFeedCommandBus
import com.anycommunity.usecase.user_feed.command.usecase.CreateUserFeedCommand
import com.anycommunity.usecase.user_feed.command.usecase.DeleteUserFeedCommandByPostDelete
import com.anycommunity.usecase.user_feed.command.usecase.DeleteUserFeedCommandByUnFollow

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
        userFeedCommandBus execute DeleteUserFeedCommandByPostDelete.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Follow.UNFOLLOW],
        groupId = KafkaGroupId.UserFeed.DELETE_BY_UNFOLLOW,
        containerFactory = LISTENER_FACTORY,
    )
    fun unFollowEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, UnFollowEvent::class.java)
        userFeedCommandBus execute DeleteUserFeedCommandByUnFollow.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Post.CREATE],
        groupId = KafkaGroupId.UserFeed.CREATE,
        containerFactory = LISTENER_FACTORY,
    )
    fun postCreatedEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, PostCreatedEvent::class.java)
        userFeedCommandBus execute CreateUserFeedCommand.from(event)
    }
}
