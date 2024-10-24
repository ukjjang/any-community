package com.jinuk.toy.mvcapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.usecase.CreatePostCommand
import com.jinuk.toy.domain.post.value.PostTitle

data class PostCreateRequest(
    val title: PostTitle,
    val content: String,
)

internal fun PostCreateRequest.toCommand(userId: Long) =
    CreatePostCommand(
        userId = userId,
        title = title,
        content = content,
    )
