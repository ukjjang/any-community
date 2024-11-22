package com.anycommunity.usecase.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.event.LikeAddedEvent
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.infra.kafka.service.KafkaProducer

@Service
class AddLikeUseCase(
    private val likeCommandService: LikeCommandService,
    private val kafkaProducer: KafkaProducer,
) {
    @Transactional
    operator fun invoke(command: AddLikeCommand) {
        val like = likeCommandService.add(command.userId, command.likeTarget)
        kafkaProducer.send(KafkaTopic.Like.ADD, LikeAddedEvent.of(like))
    }
}

data class AddLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
