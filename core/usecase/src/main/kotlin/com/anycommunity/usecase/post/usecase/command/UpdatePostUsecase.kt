package com.anycommunity.usecase.post.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.post.service.PostCommandService
import com.anycommunity.usecase.post.port.command.model.UpdatePostCommand
import com.anycommunity.usecase.post.port.command.model.UpdatePostResult

@Service
class UpdatePostUsecase(
    private val postCommandService: PostCommandService,
) {
    @Transactional
    operator fun invoke(command: UpdatePostCommand) =
        postCommandService.update(command.toInfo()).let { UpdatePostResult.from(it) }
}
