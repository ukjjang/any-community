package com.jinuk.toy.applicaiton.post.command

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle

data class CreatePostCommand(
    val userId: Long,
    val title: PostTitle,
    val content: String,
)

fun CreatePostCommand.toPost() = Post(
    userId = userId,
    title = title,
    content = content,
)
