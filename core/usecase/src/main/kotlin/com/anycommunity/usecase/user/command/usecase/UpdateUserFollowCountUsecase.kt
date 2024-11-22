package com.anycommunity.usecase.user.command.usecase

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.FollowAddedEvent
import com.anycommunity.domain.follow.event.FollowCanceledEvent
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
) {
    companion object {
        fun from(event: FollowAddedEvent) = UpdateUserFollowCountCommand(event.followRelation, CountOperation.INCREASE)

        fun from(event: FollowCanceledEvent) =
            UpdateUserFollowCountCommand(event.followRelation, CountOperation.DECREMENT)
    }
}
