package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import java.time.LocalDateTime

data class Post(
    val id: Long? = null,
    val title: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    companion object {
        fun create(command: PostCreateCommand) = with(command) {
            Post(title = title)
        }
    }
}
