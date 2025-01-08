package com.anycommunity.usecase.post.port.command.model

import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.PostCreateInfo

data class CreatePostCommand(
    val userId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
) {
    fun toInfo() = PostCreateInfo(
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
}
