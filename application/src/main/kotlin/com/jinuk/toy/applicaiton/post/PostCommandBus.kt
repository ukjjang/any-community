package com.jinuk.toy.applicaiton.post

import com.jinuk.toy.applicaiton.post.usecase.CreatePostUsecase
import com.jinuk.toy.applicaiton.post.command.CreatePostCommand
import com.jinuk.toy.domain.post.Post
import org.springframework.stereotype.Service

sealed interface PostCommandBus {
    infix fun execute(command: CreatePostCommand): Post
}

@Service
internal class PostCommandBusImpl(
    private val createPostUsecase: CreatePostUsecase
) : PostCommandBus {

    override fun execute(command: CreatePostCommand) = createPostUsecase(command)
}
