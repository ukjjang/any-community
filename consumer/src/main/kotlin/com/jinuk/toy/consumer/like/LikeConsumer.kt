package com.jinuk.toy.consumer.like

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.jinuk.toy.applicaiton.like.command.LikeCommandBus
import com.jinuk.toy.applicaiton.like.command.usecase.UpdateLikeCountCommand
import com.jinuk.toy.consumer.global.KafkaEventParser
import com.jinuk.toy.domain.like.event.LikeAddedEvent
import com.jinuk.toy.domain.like.event.LikeCanceledEvent
import com.jinuk.toy.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.jinuk.toy.infra.kafka.model.KafkaGroupId
import com.jinuk.toy.infra.kafka.model.KafkaTopic

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
    fun commentCreatedEventConsume(
        @Payload message: String,
    ) {
        val event = kafkaEventParser.parse(message, LikeAddedEvent::class.java)
        likeCommandBus execute UpdateLikeCountCommand.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Like.CANCEL],
        groupId = KafkaGroupId.Like.DECREASE_LIKE_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentDeletedEventConsume(
        @Payload message: String,
    ) {
        val event = kafkaEventParser.parse(message, LikeCanceledEvent::class.java)
        likeCommandBus execute UpdateLikeCountCommand.from(event)
    }
}
