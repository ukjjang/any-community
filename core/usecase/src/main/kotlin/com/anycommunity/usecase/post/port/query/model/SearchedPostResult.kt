package com.anycommunity.usecase.post.port.query.model

import java.time.LocalDateTime
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.definition.user.Username

data class SearchedPostResult(
    val id: Long,
    val title: PostTitle,
    val category: PostCategory,
    val userName: Username,
    val commentCount: Long,
    val likeCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
