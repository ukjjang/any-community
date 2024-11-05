package com.jinuk.toy.applicaiton.user.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.domain.user.event.FollowAddedEvent
import com.jinuk.toy.domain.user.event.FollowCanceledEvent
import com.jinuk.toy.domain.user.service.UserCommandService
import com.jinuk.toy.infra.redis.lock.distributedLock

@Service
class UpdateUserFollowCountUsecase(
    private val userCommandService: UserCommandService,
) {
    operator fun invoke(command: UpdateUserFollowCountCommand) =
        distributedLock(
            key = "UpdateUserFollowCountUsecase.invoke.${command.hashCode()}",
            transactional = true,
        ) {
            with(command) {
                val followerUserId = followRelation.followerUserId
                val followingUserId = followRelation.followingUserId

                userCommandService.updateFollowingCount(followerUserId, countDelta)
                userCommandService.updateFollowerCount(followingUserId, countDelta)
                return@with
            }
        }
}

data class UpdateUserFollowCountCommand(
    val followRelation: FollowRelation,
    val countDelta: Int,
) {
    companion object {
        fun from(event: FollowAddedEvent) = UpdateUserFollowCountCommand(event.followRelation, 1)

        fun from(event: FollowCanceledEvent) = UpdateUserFollowCountCommand(event.followRelation, -1)
    }
}
