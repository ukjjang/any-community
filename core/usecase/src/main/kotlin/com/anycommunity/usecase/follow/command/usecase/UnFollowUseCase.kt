package com.anycommunity.usecase.follow.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.FollowCanceledEvent
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.kafka.service.KafkaProducer

@Service
class UnFollowUseCase(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
    private val kafkaProducer: KafkaProducer,
) {
    @Transactional
    operator fun invoke(command: UnFollowCommand) {
        if (!userQueryService.existsById(command.followRelation.followingUserId)) {
            throw NoSuchElementException("언팔로우 대상이 존재하지 않습니다.")
        }
        followCommandService.delete(command.followRelation)

        kafkaProducer.send(KafkaTopic.Follow.CANCEL, FollowCanceledEvent(command.followRelation))
    }
}

data class UnFollowCommand(
    val followRelation: FollowRelation,
)
