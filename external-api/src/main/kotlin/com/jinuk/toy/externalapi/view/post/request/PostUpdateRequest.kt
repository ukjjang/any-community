package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.usecase.UpdatePostCommand
import com.jinuk.toy.domain.post.value.PostTitle

data class PostUpdateRequest(
    val title: PostTitle,
    val content: String,
)

internal fun PostUpdateRequest.toCommand(userId: Long, id: Long) = UpdatePostCommand(
    userId = userId,
    id = id,
    title = title,
    content = content,
)
