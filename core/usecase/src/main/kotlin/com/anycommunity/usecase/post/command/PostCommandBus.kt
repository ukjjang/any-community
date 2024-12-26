package com.anycommunity.usecase.post.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.post.command.usecase.CreatePostCommand
import com.anycommunity.usecase.post.command.usecase.CreatePostResult
import com.anycommunity.usecase.post.command.usecase.CreatePostUsecase
import com.anycommunity.usecase.post.command.usecase.DeletePostCommand
import com.anycommunity.usecase.post.command.usecase.DeletePostUsecase
import com.anycommunity.usecase.post.command.usecase.UpdatePostCommand
import com.anycommunity.usecase.post.command.usecase.UpdatePostResult
import com.anycommunity.usecase.post.command.usecase.UpdatePostUsecase

sealed interface PostCommandBus {
    infix fun execute(command: CreatePostCommand): CreatePostResult

    infix fun execute(command: UpdatePostCommand): UpdatePostResult

    infix fun execute(command: DeletePostCommand)
}

@Service
internal class PostCommandBusImpl(
    private val createPostUsecase: CreatePostUsecase,
    private val updatePostUsecase: UpdatePostUsecase,
    private val deletePostUsecase: DeletePostUsecase,
) : PostCommandBus {
    override fun execute(command: CreatePostCommand) = createPostUsecase(command)

    override fun execute(command: UpdatePostCommand) = updatePostUsecase(command)

    override fun execute(command: DeletePostCommand) = deletePostUsecase(command)
}
