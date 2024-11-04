package com.jinuk.toy.applicaiton.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.event.LikeCanceledEvent
import com.jinuk.toy.domain.like.service.LikeCommandService
import com.jinuk.toy.infra.kafka.model.KafkaTopic
import com.jinuk.toy.infra.kafka.service.KafkaProducer

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
