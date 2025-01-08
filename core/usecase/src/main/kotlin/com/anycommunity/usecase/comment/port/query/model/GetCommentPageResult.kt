package com.anycommunity.usecase.comment.port.query.model

import java.time.LocalDateTime
import com.anycommunity.definition.user.Username

data class GetCommentPageResult(
    val id: Long,
    val username: Username,
    val isViewerLike: Boolean,
    val parentCommentId: Long?,
    val likeCount: Long,
    val content: String,
    val children: List<GetCommentPageResult>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
