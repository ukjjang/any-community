package com.anycommunity.domain.user_feed.service

import org.springframework.stereotype.Service
import com.anycommunity.domain.user_feed.UserFeed
import com.anycommunity.domain.user_feed.UserFeedCreateInfo
import com.anycommunity.domain.user_feed.jpa.UserFeedRepository

@Service
class UserFeedCommandService(
    private val userFeedRepository: UserFeedRepository,
    private val userFeedQueryService: UserFeedQueryService,
) {
    fun save(userFeed: UserFeed) = userFeedRepository.save(userFeed)

    fun create(info: UserFeedCreateInfo): UserFeed {
        require(!userFeedQueryService.existsByUserIdAndPostId(info.userId, info.postId)) {
            "이미 존재하는 유저 피드입니다."
        }
        return save(UserFeed.create(info))
    }

    fun deleteByPostId(postId: Long) = userFeedRepository.deleteByPostId(postId)
    fun deleteByUserIdAndPostAuthorId(userId: Long, postAuthorId: Long) =
        userFeedRepository.deleteByUserIdAndPostAuthorId(userId, postAuthorId)
}
