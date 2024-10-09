package com.jinuk.toy.externalapi.view.post.response

import com.jinuk.toy.domain.post.Post
import java.time.LocalDateTime

data class PostCreateResponse(
    val id: Long?,
    val title: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

fun Post.toResponse() = PostCreateResponse(
    id = id,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
