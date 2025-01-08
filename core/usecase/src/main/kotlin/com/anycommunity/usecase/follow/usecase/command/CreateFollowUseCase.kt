package com.anycommunity.usecase.follow.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.follow.port.command.model.CreateFollowCommand
import com.anycommunity.usecase.user.port.command.model.UpdateUserFollowCountCommand
import com.anycommunity.usecase.user.usecase.command.internal.UpdateUserFollowCountUsecase

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
