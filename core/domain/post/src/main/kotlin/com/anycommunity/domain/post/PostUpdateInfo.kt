package com.anycommunity.domain.post

import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle

data class PostUpdateInfo(
    val id: Long,
    val userId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
)