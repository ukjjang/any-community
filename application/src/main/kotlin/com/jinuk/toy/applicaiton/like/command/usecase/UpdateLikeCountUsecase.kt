package com.jinuk.toy.applicaiton.like.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.common.value.like.LikeType
import com.jinuk.toy.domain.comment.service.CommentCommandService
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.event.LikeAddedEvent
import com.jinuk.toy.domain.like.event.LikeCanceledEvent
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.infra.redis.lock.distributedLock

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
