package com.jinuk.toy.mvcapi.view.post.response

import java.time.LocalDateTime
import com.jinuk.toy.applicaiton.post.query.usecase.PostDetailResult
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username

data class PostDetailResponse(
    val id: Long?,
    val userId: Long,
    val username: Username,
    val title: PostTitle,
    val category: PostCategoryResponse,
    val content: String,
    val isViewerLike: Boolean,
    val likeCount: Long,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)

internal fun PostDetailResult.toResponse() =
    with(this) {
        PostDetailResponse(
            id = id,
            userId = userId,
            username = username,
            title = title,
            category = PostCategoryResponse.of(category),
            content = content,
            isViewerLike = isViewerLike,
            likeCount = likeCount,
            commentCount = commentCount,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
