package com.jinuk.toy.domain.post

import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle

data class PostUpdateInfo(
    val id: Long,
    val userId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
)
