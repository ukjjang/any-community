package com.anycommunity.usecase.post.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.post.port.command.model.CreatePostCommand
import com.anycommunity.usecase.post.port.command.model.CreatePostResult
import com.anycommunity.usecase.post.port.command.model.DeletePostCommand
import com.anycommunity.usecase.post.port.command.model.UpdatePostCommand
import com.anycommunity.usecase.post.port.command.model.UpdatePostResult
import com.anycommunity.usecase.post.usecase.command.CreatePostUsecase
import com.anycommunity.usecase.post.usecase.command.DeletePostUsecase
import com.anycommunity.usecase.post.usecase.command.UpdatePostUsecase

sealed interface PostCommandPort {
    infix fun execute(command: CreatePostCommand): CreatePostResult

    infix fun execute(command: UpdatePostCommand): UpdatePostResult

    infix fun execute(command: DeletePostCommand)
}

@Service
internal class PostCommandPortImpl(
    private val createPostUsecase: CreatePostUsecase,
    private val updatePostUsecase: UpdatePostUsecase,
    private val deletePostUsecase: DeletePostUsecase,
) : PostCommandPort {
    override fun execute(command: CreatePostCommand) = createPostUsecase(command)

    override fun execute(command: UpdatePostCommand) = updatePostUsecase(command)

    override fun execute(command: DeletePostCommand) = deletePostUsecase(command)
}
