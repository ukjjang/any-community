package com.anycommunity.usecase.like.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.usecase.like.command.usecase.internal.UpdateLikeCountCommand
import com.anycommunity.usecase.like.command.usecase.internal.UpdateLikeCountUsecase

@Service
class AddLikeUseCase(
    private val likeCommandService: LikeCommandService,
    private val updateLikeCountUsecase: UpdateLikeCountUsecase,
) {
    @Transactional
    operator fun invoke(command: AddLikeCommand) {
        likeCommandService.add(command.userId, command.likeTarget)
            .also {
                updateLikeCount(command.likeTarget)
            }
    }

    private fun updateLikeCount(likeTarget: LikeTarget) {
        val command = UpdateLikeCountCommand(
            likeTarget = likeTarget,
            countOperation = CountOperation.INCREASE,
        )
        updateLikeCountUsecase(command)
    }
}

data class AddLikeCommand(
    val userId: Long,
    val likeTarget: LikeTarget,
)
