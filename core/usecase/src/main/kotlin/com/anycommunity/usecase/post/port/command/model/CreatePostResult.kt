package com.anycommunity.usecase.post.port.command.model

import java.time.LocalDateTime
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.Post

data class CreatePostResult(
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post) = with(post) {
            CreatePostResult(
                id = id,
                title = title,
                category = category,
                content = content,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
