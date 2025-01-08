package com.anycommunity.usecase.user.usecase.command.internal

import org.springframework.stereotype.Service
import com.anycommunity.domain.user.service.UserCommandService
import com.anycommunity.infra.redis.lock.distributedLock
import com.anycommunity.usecase.user.port.command.model.UpdateUserFollowCountCommand

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
