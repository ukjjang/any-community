package com.jinuk.toy.applicaiton.post.command.port

import com.jinuk.toy.domain.post.service.PostCreateService
import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import org.springframework.stereotype.Service

@Service
class PostCreateApplication(
    private val postCreateService: PostCreateService,
) {

    operator fun invoke(command: PostCreateCommand) = postCreateService.create(command)
}
