package com.jinuk.toy.applicaiton.post.command.dto

import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import com.jinuk.toy.domain.post.value.PostTitle

data class PostCreateCommandDto(
    val title: PostTitle,
)

internal fun PostCreateCommandDto.toCommand() = PostCreateCommand(title = title)
