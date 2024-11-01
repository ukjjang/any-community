package com.jinuk.toy.mvcapi.view.post.response

import com.jinuk.toy.applicaiton.post.query.usecase.SearchedPostResult
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username
import java.time.LocalDateTime

class PostSearchResponse(
    val id: Long,
    val title: PostTitle,
    val userName: Username,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

fun SearchedPostResult.toResponse() =
    PostSearchResponse(
        id = id,
        title = title,
        userName = userName,
        commentCount = commentCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
