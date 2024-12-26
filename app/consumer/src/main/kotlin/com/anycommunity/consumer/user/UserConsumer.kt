package com.anycommunity.consumer.user

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import com.anycommunity.consumer.global.KafkaEventParser
import com.anycommunity.definition.global.kafka.KafkaGroupId
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.follow.event.FollowCanceledEvent
import com.anycommunity.infra.kafka.KafkaConfig.Companion.LISTENER_FACTORY
import com.anycommunity.usecase.user.command.UserCommandBus
import com.anycommunity.usecase.user.command.usecase.internal.UpdateUserFollowCountCommand

@Component
class UserConsumer(
    private val kafkaEventParser: KafkaEventParser,
    private val userCommandBus: UserCommandBus,
) {
    @KafkaListener(
        topics = [KafkaTopic.Follow.CANCEL],
        groupId = KafkaGroupId.User.DECREASE_FOLLOW_COUNT,
        containerFactory = LISTENER_FACTORY,
    )
    fun followCanceledEventConsume(@Payload message: String) {
        val event = kafkaEventParser.parse(message, FollowCanceledEvent::class.java)
        userCommandBus execute UpdateUserFollowCountCommand.from(event)
    }
}
