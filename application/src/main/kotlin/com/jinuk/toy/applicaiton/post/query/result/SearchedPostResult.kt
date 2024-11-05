package com.jinuk.toy.applicaiton.post.query.result

import java.time.LocalDateTime
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username

data class SearchedPostResult(
    val id: Long,
    val title: PostTitle,
    val userName: Username,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
