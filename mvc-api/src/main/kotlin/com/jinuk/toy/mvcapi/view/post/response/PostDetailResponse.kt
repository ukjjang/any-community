package com.jinuk.toy.mvcapi.view.post.response

import java.time.LocalDateTime
import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username

data class PostDetailResponse(
    val id: Long?,
    val userId: Long,
    val username: Username,
    val title: PostTitle,
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
            content = content,
            isViewerLike = isViewerLike,
            likeCount = likeCount,
            commentCount = commentCount,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
