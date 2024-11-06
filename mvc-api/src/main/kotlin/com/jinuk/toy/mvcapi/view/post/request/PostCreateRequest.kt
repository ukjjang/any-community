package com.jinuk.toy.mvcapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.usecase.CreatePostCommand
import com.jinuk.toy.constant.post.PostCategory
import com.jinuk.toy.domain.post.value.PostTitle

data class PostCreateRequest(
    val title: PostTitle,
    val category: PostCategory = PostCategory.ETC,
    val content: String,
)

internal fun PostCreateRequest.toCommand(userId: Long) =
    CreatePostCommand(
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
