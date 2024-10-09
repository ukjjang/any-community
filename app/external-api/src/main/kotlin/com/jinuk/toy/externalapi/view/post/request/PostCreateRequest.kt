package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.dto.PostCreateCommandDto

data class PostCreateRequest(
    val title: String,
)

internal fun PostCreateRequest.toCommand() = PostCreateCommandDto(title)
