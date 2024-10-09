package com.jinuk.toy.applicaiton.post.command.adaptor

import com.jinuk.toy.applicaiton.post.command.port.PostCreateApplication
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import org.springframework.stereotype.Service

sealed interface PostCommandBus {
    infix fun execute(command: PostCreateCommand): Post
}

@Service
internal class PostCommandBusImpl(
    private val postCreateApplication: PostCreateApplication
) : PostCommandBus {

    override fun execute(command: PostCreateCommand) = postCreateApplication(command)
}
