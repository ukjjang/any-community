package com.jinuk.toy.applicaiton.post

import com.jinuk.toy.applicaiton.post.command.CreatePostCommand
import com.jinuk.toy.applicaiton.post.command.DeletePostCommand
import com.jinuk.toy.applicaiton.post.command.UpdatePostCommand
import com.jinuk.toy.applicaiton.post.usecase.CreatePostUsecase
import com.jinuk.toy.applicaiton.post.usecase.DeletePostUsecase
import com.jinuk.toy.applicaiton.post.usecase.UpdatePostUsecase
import com.jinuk.toy.domain.post.Post
import org.springframework.stereotype.Service

sealed interface PostCommandBus {
    infix fun execute(command: CreatePostCommand): Post
    infix fun execute(command: UpdatePostCommand): Post
    infix fun execute(command: DeletePostCommand)
}

@Service
internal class PostCommandBusImpl(
    private val createPostUsecase: CreatePostUsecase,
    private val updatePostUsecase: UpdatePostUsecase,
    private val deletePostUsecase: DeletePostUsecase
) : PostCommandBus {

    override fun execute(command: CreatePostCommand) = createPostUsecase(command)
    override fun execute(command: UpdatePostCommand) = updatePostUsecase(command)
    override fun execute(command: DeletePostCommand) = deletePostUsecase(command)
}
