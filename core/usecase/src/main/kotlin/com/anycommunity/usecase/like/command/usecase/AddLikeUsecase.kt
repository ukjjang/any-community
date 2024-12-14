package com.anycommunity.usecase.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.event.LikeAddedEvent
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator

@Service
class AddLikeUseCase(
    private val likeCommandService: LikeCommandService,
    private val outboxCreator: OutboxCreator,
) {
    @Transactional
    operator fun invoke(command: AddLikeCommand) {
        likeCommandService.add(command.userId, command.likeTarget)
            .also { outboxCreator.create(KafkaTopic.Like.ADD, LikeAddedEvent.of(it)) }
    }
}

data class AddLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
