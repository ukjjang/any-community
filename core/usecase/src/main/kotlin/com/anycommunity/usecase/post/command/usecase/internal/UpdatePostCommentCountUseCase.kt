package com.anycommunity.usecase.post.command.usecase.internal

import org.springframework.stereotype.Service
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.comment.event.CommentCreatedEvent
import com.anycommunity.domain.comment.event.CommentDeletedEvent
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.infra.redis.lock.distributedLock

@Service
class UpdatePostCommentCountUseCase(
    private val postCommandService: PostCommandService,
) {
    operator fun invoke(command: UpdatePostCommentCountCommand) = distributedLock(
        key = "UpdatePostCommentCountUseCase:${command.postId}",
        transactional = true,
    ) {
        postCommandService.updateCommentCount(command.postId, command.countOperation)
        return@distributedLock
    }
}

data class UpdatePostCommentCountCommand(
    val postId: Long,
    val countOperation: CountOperation,
) {
    companion object {
        fun from(event: CommentCreatedEvent) = UpdatePostCommentCountCommand(event.postId, CountOperation.INCREASE)

        fun from(event: CommentDeletedEvent) = UpdatePostCommentCountCommand(event.postId, CountOperation.DECREMENT)
    }
}
