package com.anycommunity.usecase.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.event.LikeCanceledEvent
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator

@Service
class CancelLikeUsecase(
    private val likeCommandService: LikeCommandService,
    private val outboxCreator: OutboxCreator,
) {
    @Transactional
    operator fun invoke(command: CancelLikeCommand) {
        likeCommandService.delete(command.userId, command.likeTarget)

        val event = LikeCanceledEvent(
            userId = command.userId,
            likeTarget = command.likeTarget,
        )
        outboxCreator.create(KafkaTopic.Like.CANCEL, event)
    }
}

data class CancelLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
