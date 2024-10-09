package com.jinuk.toy.applicaiton.post.command.dto

import com.jinuk.toy.domain.post.service.command.PostCreateCommand

data class PostCreateCommandDto(
    val title: String,
)

internal fun PostCreateCommandDto.toCommand() = PostCreateCommand(title = title)
