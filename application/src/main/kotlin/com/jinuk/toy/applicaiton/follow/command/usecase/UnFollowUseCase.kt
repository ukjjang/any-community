package com.jinuk.toy.applicaiton.follow.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.domain.user.service.FollowCommandService
import com.jinuk.toy.domain.user.service.UserQueryService

@Service
class UnFollowUseCase(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
) {
    @Transactional
    operator fun invoke(command: UnFollowCommand) {
        if (!userQueryService.existsById(command.followRelation.followingUserId)) {
            throw NoSuchElementException("언팔로우 대상이 존재하지 않습니다.")
        }
        followCommandService.delete(command.followRelation)
    }
}

data class UnFollowCommand(
    val followRelation: FollowRelation,
)
