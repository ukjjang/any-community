package com.jinuk.toy.applicaiton.post.command.adaptor

import com.jinuk.toy.applicaiton.post.command.dto.PostCreateCommandDto
import com.jinuk.toy.applicaiton.post.command.port.PostCreateApplication
import com.jinuk.toy.domain.post.Post
import org.springframework.stereotype.Service

sealed interface PostCommandBus {
    infix fun execute(command: PostCreateCommandDto): Post
}

@Service
internal class PostCommandBusImpl(
    private val postCreateApplication: PostCreateApplication
) : PostCommandBus {

    override fun execute(command: PostCreateCommandDto) = postCreateApplication(command)
}
