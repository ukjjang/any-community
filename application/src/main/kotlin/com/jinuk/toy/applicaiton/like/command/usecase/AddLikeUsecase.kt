package com.jinuk.toy.applicaiton.like.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeCommandService

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
