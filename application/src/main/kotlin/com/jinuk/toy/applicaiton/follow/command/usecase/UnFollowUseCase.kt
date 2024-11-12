package com.jinuk.toy.applicaiton.follow.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.define.global.kafka.KafkaTopic
import com.jinuk.toy.domain.follow.FollowRelation
import com.jinuk.toy.domain.follow.event.FollowCanceledEvent
import com.jinuk.toy.domain.follow.service.FollowCommandService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.infra.kafka.service.KafkaProducer

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
