package com.jinuk.toy.applicaiton.like.command.usecase

import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeCommandService
import org.springframework.stereotype.Service

@Service
class AddLikeUseCase(
    private val likeCommandService: LikeCommandService,
) {
    operator fun invoke(command: AddLikeCommand) {
        likeCommandService.add(command.userId, command.likeTarget)
    }
}

data class AddLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
