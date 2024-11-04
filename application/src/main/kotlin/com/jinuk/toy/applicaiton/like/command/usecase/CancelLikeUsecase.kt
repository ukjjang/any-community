package com.jinuk.toy.applicaiton.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeCommandService

@Service
class CancelLikeUsecase(
    private val likeCommandService: LikeCommandService,
) {
    @Transactional
    operator fun invoke(command: CancelLikeCommand) {
        likeCommandService.delete(command.userId, command.likeTarget)
    }
}

data class CancelLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
