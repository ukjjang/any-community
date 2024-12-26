package com.anycommunity.domain.user_feed

data class UserFeedCreateInfo(
    val userId: List<Long>,
    val postId: Long,
    val postAuthorId: Long,
)
