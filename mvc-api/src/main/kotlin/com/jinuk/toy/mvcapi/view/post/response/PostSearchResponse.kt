package com.jinuk.toy.mvcapi.view.post.response

import java.time.LocalDateTime
import com.jinuk.toy.applicaiton.post.query.result.SearchedPostResult
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username

class PostSearchResponse(
    val id: Long,
    val title: PostTitle,
    val category: PostCategoryResponse,
    val userName: Username,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

fun SearchedPostResult.toResponse() =
    PostSearchResponse(
        id = id,
        title = title,
        category = PostCategoryResponse.of(category),
        userName = userName,
        commentCount = commentCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
