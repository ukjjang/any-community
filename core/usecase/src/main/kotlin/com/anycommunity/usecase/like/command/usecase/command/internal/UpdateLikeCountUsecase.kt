package com.anycommunity.usecase.like.command.usecase.command.internal

import org.springframework.stereotype.Service
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.comment.service.CommentCommandService
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.infra.redis.lock.distributedLock
import com.anycommunity.usecase.like.command.port.command.model.UpdateLikeCountCommand

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
