package com.anycommunity.usecase.follow.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.FollowAddedEvent
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.kafka.service.KafkaProducer

@Service
class CreateFollowUseCase(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
    private val kafkaProducer: KafkaProducer,
) {
    @Transactional
    operator fun invoke(command: CreateFollowCommand) {
        if (!userQueryService.existsById(command.followRelation.followingUserId)) {
            throw NoSuchElementException("팔로우 대상이 존재하지 않습니다.")
        }
        followCommandService.create(command.followRelation)

        kafkaProducer.send(KafkaTopic.Follow.ADD, FollowAddedEvent(command.followRelation))
    }
}

data class CreateFollowCommand(
    val followRelation: FollowRelation,
)
