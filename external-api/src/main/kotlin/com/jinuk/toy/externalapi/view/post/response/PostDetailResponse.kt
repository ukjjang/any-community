package com.jinuk.toy.externalapi.view.post.response

import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username
import java.time.LocalDateTime

data class PostDetailResponse(
    val id: Long?,
    val userId: Long,
    val username: Username,
    val title: PostTitle,
    val content: String,
    val comments: List<CommentDetailResponse>,
    val commentSize: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    data class CommentDetailResponse(
        val id: Long?,
        val userId: Long,
        val username: Username,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    )
}

internal fun PostDetailResult.toResponse() = with(this) {
    PostDetailResponse(
        id = id,
        userId = userId,
        username = username,
        title = title,
        content = content,
        comments = comments.map { it.toResponse() },
        commentSize = comments.size,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}

internal fun PostDetailResult.CommentDetail.toResponse() = with(this) {
    PostDetailResponse.CommentDetailResponse(
        id = id,
        userId = userId,
        username = username,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
