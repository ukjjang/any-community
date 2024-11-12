package com.jinuk.toy.applicaiton.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.define.global.kafka.KafkaTopic
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.event.LikeAddedEvent
import com.jinuk.toy.domain.like.service.LikeCommandService
import com.jinuk.toy.infra.kafka.service.KafkaProducer

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
