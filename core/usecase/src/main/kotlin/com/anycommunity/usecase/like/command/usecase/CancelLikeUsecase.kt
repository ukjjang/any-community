package com.anycommunity.usecase.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.event.LikeCanceledEvent
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.infra.kafka.service.KafkaProducer

@Service
class CancelLikeUsecase(
    private val likeCommandService: LikeCommandService,
    private val kafkaProducer: KafkaProducer,
) {
    @Transactional
    operator fun invoke(command: CancelLikeCommand) {
        likeCommandService.delete(command.userId, command.likeTarget)

        kafkaProducer.send(
            topic = KafkaTopic.Like.CANCEL,
            payload = LikeCanceledEvent(userId = command.userId, likeTarget = command.likeTarget),
        )
    }
}

data class CancelLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
