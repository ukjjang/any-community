package com.jinuk.toy.applicaiton.post.command.port

import com.jinuk.toy.applicaiton.post.command.dto.PostCreateCommandDto
import com.jinuk.toy.applicaiton.post.command.dto.toCommand
import com.jinuk.toy.domain.post.service.PostCreateService
import org.springframework.stereotype.Service

@Service
class PostCreateApplication(
    private val postCreateService: PostCreateService,
) {

    operator fun invoke(commandDto: PostCreateCommandDto) = postCreateService(commandDto.toCommand())
}
