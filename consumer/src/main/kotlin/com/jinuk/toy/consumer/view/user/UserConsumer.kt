package com.jinuk.toy.consumer.view.user

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.jinuk.toy.applicaiton.user.command.UserCommandBus
import com.jinuk.toy.applicaiton.user.command.usecase.UpdateUserFollowCountCommand
import com.jinuk.toy.consumer.global.KafkaEventParser
import com.jinuk.toy.domain.user.event.FollowAddedEvent
import com.jinuk.toy.domain.user.event.FollowCanceledEvent
import com.jinuk.toy.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.jinuk.toy.infra.kafka.model.KafkaGroupId
import com.jinuk.toy.infra.kafka.model.KafkaTopic

@Component
class UserConsumer(
    private val kafkaEventParser: KafkaEventParser,
    private val userCommandBus: UserCommandBus,
) {
    @KafkaListener(
        topics = [KafkaTopic.Follow.ADD],
        groupId = KafkaGroupId.User.INCREASE_FOLLOW_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentCreatedEventConsume(
        @Payload message: String,
    ) {
        val event = kafkaEventParser.parse(message, FollowAddedEvent::class.java)
        userCommandBus execute UpdateUserFollowCountCommand.from(event)
    }

    @KafkaListener(
        topics = [KafkaTopic.Follow.CANCEL],
        groupId = KafkaGroupId.User.DECREASE_FOLLOW_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun commentDeletedEventConsume(
        @Payload message: String,
    ) {
        val event = kafkaEventParser.parse(message, FollowCanceledEvent::class.java)
        userCommandBus execute UpdateUserFollowCountCommand.from(event)
    }
}