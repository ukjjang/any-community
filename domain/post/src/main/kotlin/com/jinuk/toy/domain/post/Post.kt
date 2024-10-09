package com.jinuk.toy.domain.post

import java.time.LocalDateTime

data class Post(
    val id: Long? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)
