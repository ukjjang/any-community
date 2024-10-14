package com.jinuk.toy.applicaiton.post.command

data class DeletePostCommand(
    val userId: Long,
    val id: Long,
)
