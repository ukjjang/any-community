package com.jinuk.toy.applicaiton.like.command.usecase

import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeCommandService
import org.springframework.stereotype.Service

@Service
class CancelLikeUsecase(
    private val likeCommandService: LikeCommandService,
) {
    operator fun invoke(command: CancelLikeCommand) {
        likeCommandService.delete(command.userId, command.likeTarget)
    }
}

data class CancelLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
