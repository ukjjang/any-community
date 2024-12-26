package com.anycommunity.usecase.follow.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.user.command.usecase.internal.UpdateUserFollowCountCommand
import com.anycommunity.usecase.user.command.usecase.internal.UpdateUserFollowCountUsecase

@Service
class CreateFollowUseCase(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
    private val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase,
) {
    @Transactional
    operator fun invoke(command: CreateFollowCommand) {
        if (!userQueryService.existsById(command.followRelation.followingUserId)) {
            throw NoSuchElementException("팔로우 대상이 존재하지 않습니다.")
        }
        followCommandService.create(command.followRelation)
        updateUserFollowCount(command.followRelation)
    }

    private fun updateUserFollowCount(followRelation: FollowRelation) {
        val command = UpdateUserFollowCountCommand(
            followRelation = followRelation,
            countOperation = CountOperation.INCREASE,
        )
        updateUserFollowCountUsecase(command)
    }
}

data class CreateFollowCommand(
    val followRelation: FollowRelation,
)
