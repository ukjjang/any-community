package com.jinuk.toy.applicaiton.follow.command.usecase

import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.domain.user.service.FollowCommandService
import com.jinuk.toy.domain.user.service.UserQueryService
import org.springframework.stereotype.Service

@Service
class UnFollowUseCase(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
) {
    operator fun invoke(command: UnFollowCommand) {
        if (!userQueryService.existsById(command.followRelation.followingUserId)) {
            throw NoSuchElementException("언팔로우 대상이 존재하지 않습니다.")
        }
        followCommandService.delete(command.followRelation)
    }
}

data class UnFollowCommand(
    val followRelation: FollowRelation
)
