package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import com.jinuk.toy.domain.post.value.PostTitle

data class PostCreateRequest(
    val title: PostTitle,
    val content: String,
)

internal fun PostCreateRequest.toCommand(userId: Long) = PostCreateCommand(
    userId = userId,
    title = title,
    content = content,
)
