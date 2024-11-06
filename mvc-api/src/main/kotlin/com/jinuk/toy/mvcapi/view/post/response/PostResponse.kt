package com.jinuk.toy.mvcapi.view.post.response

import java.time.LocalDateTime
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle

data class PostResponse(
    val id: Long?,
    val title: PostTitle,
    val category: PostCategoryResponse,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

fun Post.toResponse() =
    PostResponse(
        id = id,
        title = title,
        category = PostCategoryResponse.of(category),
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
