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
    fun saveAll(userFeeds: List<UserFeed>) = userFeedRepository.saveAll(userFeeds)

    fun create(info: UserFeedCreateInfo): List<UserFeed> {
        val exists = userFeedQueryService.findByPostId(info.postId).map { it.userId }.toSet()
        val createInfo = UserFeedCreateInfo(
            userId = info.userId.filterNot { exists.contains(it) },
            postId = info.postId,
            postAuthorId = info.postAuthorId,
        )
        return saveAll(UserFeed.create(createInfo))
    }

    fun deleteByPostId(postId: Long) = userFeedRepository.deleteByPostId(postId)
    fun deleteByUserIdAndPostAuthorId(userId: Long, postAuthorId: Long) =
        userFeedRepository.deleteByUserIdAndPostAuthorId(userId, postAuthorId)
}
