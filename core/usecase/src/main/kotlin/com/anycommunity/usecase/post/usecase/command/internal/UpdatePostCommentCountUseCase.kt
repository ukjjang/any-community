package com.anycommunity.usecase.post.usecase.command.internal

import org.springframework.stereotype.Service
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.infra.redis.lock.distributedLock
import com.anycommunity.usecase.post.port.command.model.UpdatePostCommentCountCommand

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
