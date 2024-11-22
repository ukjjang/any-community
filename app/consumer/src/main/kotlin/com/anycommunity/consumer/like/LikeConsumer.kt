package com.anycommunity.consumer.like

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.anycommunity.consumer.global.KafkaEventParser
import com.anycommunity.definition.global.kafka.KafkaGroupId
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.like.event.LikeAddedEvent
import com.anycommunity.domain.like.event.LikeCanceledEvent
import com.anycommunity.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.anycommunity.usecase.like.command.LikeCommandBus
import com.anycommunity.usecase.like.command.usecase.UpdateLikeCountCommand

@Component
class LikeConsumer(
    private val kafkaEventParser: KafkaEventParser,
    private val likeCommandBus: LikeCommandBus,
) {
    @KafkaListener(
        topics = [KafkaTopic.Like.ADD],
        groupId = KafkaGroupId.Like.INCREASE_LIKE_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentCreatedEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, LikeAddedEvent::class.java)
        likeCommandBus execute UpdateLikeCountCommand.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Like.CANCEL],
        groupId = KafkaGroupId.Like.DECREASE_LIKE_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentDeletedEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, LikeCanceledEvent::class.java)
        likeCommandBus execute UpdateLikeCountCommand.from(event)
    }
}
