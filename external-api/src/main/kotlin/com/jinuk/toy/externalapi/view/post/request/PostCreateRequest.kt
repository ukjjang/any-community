package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.CreatePostCommand
import com.jinuk.toy.domain.post.value.PostTitle

data class PostCreateRequest(
    val title: PostTitle,
    val content: String,
)

internal fun PostCreateRequest.toCommand(userId: Long) = CreatePostCommand(
    userId = userId,
    title = title,
    content = content,
)
