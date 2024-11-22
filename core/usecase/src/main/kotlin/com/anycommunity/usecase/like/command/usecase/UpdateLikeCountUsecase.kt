package com.anycommunity.usecase.like.command.usecase

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.event.LikeAddedEvent
import com.anycommunity.domain.like.event.LikeCanceledEvent
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.infra.redis.lock.distributedLock

@Service
class UpdateLikeCountUsecase(
    private val postCommandService: PostCommandService,
    private val commentCommandService: CommentCommandService,
) {
    operator fun invoke(command: UpdateLikeCountCommand) = with(command) {
        distributedLock(
            key = "UpdateLikeCountUsecase:${command.likeTarget}",
            transactional = true,
        ) {
            when (likeTarget.type) {
                LikeType.POST -> postCommandService.updateLikeCount(
                    postId = likeTarget.id.toLong(),
                    countOperation = countOperation,
                )

                LikeType.COMMENT -> commentCommandService.updateLikeCount(
                    commentId = likeTarget.id.toLong(),
                    countOperation = countOperation,
                )
            }
            return@distributedLock
        }
    }
}

data class UpdateLikeCountCommand(
    val likeTarget: LikeTarget,
    val countOperation: CountOperation,
) {
    companion object {
        fun from(event: LikeAddedEvent) = with(event) {
            UpdateLikeCountCommand(likeTarget, CountOperation.INCREASE)
        }

        fun from(event: LikeCanceledEvent) = with(event) {
            UpdateLikeCountCommand(likeTarget, CountOperation.DECREMENT)
        }
    }
}
