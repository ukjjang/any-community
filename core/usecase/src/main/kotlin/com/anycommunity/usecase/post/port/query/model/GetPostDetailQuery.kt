package com.anycommunity.usecase.post.port.query.model

data class GetPostDetailQuery(
    val id: Long,
    val viewerId: Long? = null,
)
