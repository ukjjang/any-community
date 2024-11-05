package com.jinuk.toy.applicaiton.post.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.constant.global.CountOperation
import com.jinuk.toy.domain.comment.event.CommentCreatedEvent
import com.jinuk.toy.domain.comment.event.CommentDeletedEvent
import com.jinuk.toy.domain.post.service.PostCommandService
import com.jinuk.toy.infra.redis.lock.distributedLock

@Service
class UpdatePostCommentCountUseCase(
    private val postCommandService: PostCommandService,
) {
    operator fun invoke(command: UpdatePostCommentCountCommand) =
        distributedLock(
            key = "UpdatePostCommentCountUseCase.invoke.${command.postId}",
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
