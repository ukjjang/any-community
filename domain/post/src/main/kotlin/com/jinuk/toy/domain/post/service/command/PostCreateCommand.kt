package com.jinuk.toy.domain.post.service.command

import com.jinuk.toy.domain.post.value.PostTitle

data class PostCreateCommand(
    val title: PostTitle
)
