package com.anycommunity.usecase.user.command.usecase.internal

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.user.service.UserCommandService
import com.anycommunity.infra.redis.lock.distributedLock

@Service
class UpdateUserFollowCountUsecase(
    private val userCommandService: UserCommandService,
) {
    operator fun invoke(command: UpdateUserFollowCountCommand) = distributedLock(
        key = "UpdateUserFollowCountUsecase:${command.hashCode()}",
        transactional = true,
    ) {
        with(command) {
            val followerUserId = followRelation.followerUserId
            val followingUserId = followRelation.followingUserId

            userCommandService.updateFollowingCount(followerUserId, countOperation)
            userCommandService.updateFollowerCount(followingUserId, countOperation)
            return@with
        }
    }
}

data class UpdateUserFollowCountCommand(
    val followRelation: FollowRelation,
    val countOperation: CountOperation,
)
