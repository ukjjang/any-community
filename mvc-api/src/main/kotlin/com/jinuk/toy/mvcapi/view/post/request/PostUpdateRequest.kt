package com.jinuk.toy.mvcapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.usecase.UpdatePostCommand
import com.jinuk.toy.constant.post.PostCategory
import com.jinuk.toy.domain.post.value.PostTitle

data class PostUpdateRequest(
    val title: PostTitle,
    val category: PostCategory = PostCategory.ETC,
    val content: String,
)

internal fun PostUpdateRequest.toCommand(
    userId: Long,
    id: Long,
) = UpdatePostCommand(
    userId = userId,
    id = id,
    title = title,
    category = category,
    content = content,
)
