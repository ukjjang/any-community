package com.anycommunity.domain.user_feed

data class UserFeedCreateInfo(
    val userId: Long,
    val postId: Long,
    val postAuthorId: Long,
)
