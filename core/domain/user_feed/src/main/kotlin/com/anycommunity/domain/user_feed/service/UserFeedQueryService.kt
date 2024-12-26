package com.anycommunity.domain.user_feed.service

import org.springframework.stereotype.Service
import com.anycommunity.domain.user_feed.jpa.UserFeedRepository

@Service
class UserFeedQueryService(
    private val userFeedRepository: UserFeedRepository,
) {
    fun existsByUserIdAndPostId(userId: Long, postId: Long) = userFeedRepository.existsByUserIdAndPostId(userId, postId)
}
