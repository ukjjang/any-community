package com.jinuk.toy.applicaiton.user.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.domain.follow.FollowRelation
import com.jinuk.toy.domain.follow.event.FollowAddedEvent
import com.jinuk.toy.domain.follow.event.FollowCanceledEvent
import com.jinuk.toy.domain.user.service.UserCommandService
import com.jinuk.toy.infra.redis.lock.distributedLock

@Service
class UpdateUserFollowCountUsecase(
    private val userCommandService: UserCommandService,
) {
    operator fun invoke(command: UpdateUserFollowCountCommand) = with(command) {
        distributedLock(
            key = "UpdateUserFollowCountUsecase:${command.hashCode()}",
            transactional = true,
        ) {
            userCommandService.updateFollowingCount(followRelation.followerUserId, countOperation)
            userCommandService.updateFollowerCount(followRelation.followingUserId, countOperation)
            return@distributedLock
        }
    }
}

data class UpdateUserFollowCountCommand(
    val followRelation: FollowRelation,
    val countOperation: CountOperation,
) {
    companion object {
        fun from(event: FollowAddedEvent) = UpdateUserFollowCountCommand(event.followRelation, CountOperation.INCREASE)

        fun from(event: FollowCanceledEvent) = UpdateUserFollowCountCommand(
            event.followRelation,
            CountOperation.DECREMENT,
        )
    }
}
