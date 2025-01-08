package com.anycommunity.usecase.post.port.command.model

import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.PostUpdateInfo

data class UpdatePostCommand(
    val userId: Long,
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
) {
    fun toInfo() = PostUpdateInfo(
        id = id,
        userId = userId,
        title = title,
        category = category,
        content = content,
    )
}
