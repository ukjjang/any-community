package com.jinuk.toy.externalapi.view.post.response

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle
import java.time.LocalDateTime

data class PostResponse(
    val id: Long?,
    val title: PostTitle,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

fun Post.toResponse() = PostResponse(
    id = id,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
