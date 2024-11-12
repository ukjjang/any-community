package com.jinuk.toy.applicaiton.like.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.constant.global.CountOperation
import com.jinuk.toy.constant.like.LikeType
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
    operator fun invoke(command: UpdateLikeCountCommand) =
        distributedLock(
            key = "UpdateLikeCountUsecase:${command.likeTarget}",
            transactional = true,
        ) {
            val likeTarget = command.likeTarget
            when (likeTarget.type) {
                LikeType.POST ->
                    postCommandService.updateLikeCount(
                        likeTarget.id.toLong(),
                        command.countOperation,
                    )

                LikeType.COMMENT ->
                    commentCommandService.updateLikeCount(
                        likeTarget.id.toLong(),
                        command.countOperation,
                    )
            }
            return@distributedLock
        }
}

data class UpdateLikeCountCommand(
    val likeTarget: LikeTarget,
    val countOperation: CountOperation,
) {
    companion object {
        fun from(event: LikeAddedEvent) = with(event) { UpdateLikeCountCommand(likeTarget, CountOperation.INCREASE) }

        fun from(event: LikeCanceledEvent) =
            with(
                event,
            ) { UpdateLikeCountCommand(likeTarget, CountOperation.DECREMENT) }
    }
}
