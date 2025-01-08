package com.anycommunity.usecase.follow.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.UnFollowEvent
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.follow.port.command.model.UnFollowCommand
import com.anycommunity.usecase.user.port.command.model.UpdateUserFollowCountCommand
import com.anycommunity.usecase.user.usecase.command.internal.UpdateUserFollowCountUsecase

@Service
class UnFollowUseCase(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
    private val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase,
    private val outboxCreator: OutboxCreator,
) {
    @Transactional
    operator fun invoke(command: UnFollowCommand) {
        if (!userQueryService.existsById(command.followRelation.followingUserId)) {
            throw NoSuchElementException("언팔로우 대상이 존재하지 않습니다.")
        }
        followCommandService.delete(command.followRelation)
        updateUserFollowCount(command.followRelation)

        outboxCreator.create(KafkaTopic.Follow.UNFOLLOW, UnFollowEvent(command.followRelation))
    }

    private fun updateUserFollowCount(followRelation: FollowRelation) {
        val command = UpdateUserFollowCountCommand(
            followRelation = followRelation,
            countOperation = CountOperation.DECREMENT,
        )
        updateUserFollowCountUsecase(command)
    }
}
