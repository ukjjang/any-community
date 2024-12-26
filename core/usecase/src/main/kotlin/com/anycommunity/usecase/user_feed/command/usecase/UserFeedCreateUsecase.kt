package com.anycommunity.usecase.user_feed.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.post.event.PostCreatedEvent
import com.anycommunity.domain.user_feed.UserFeedCreateInfo
import com.anycommunity.domain.user_feed.service.UserFeedCommandService

@Service
class UserFeedCreateUsecase(
    private val userFeedCommandService: UserFeedCommandService,
    private val followQueryService: FollowQueryService,
) {
    @Transactional
    operator fun invoke(command: UserFeedCreateCommand) {
        val followerUserIds = followQueryService.findByFollowingUserId(command.userId).map { it.followerUserId }
        val createInfo = UserFeedCreateInfo(followerUserIds, command.postId, command.userId)
        userFeedCommandService.create(createInfo)
    }
}

data class UserFeedCreateCommand(
    val postId: Long,
    val userId: Long,
) {
    companion object {
        fun from(event: PostCreatedEvent) = UserFeedCreateCommand(event.id, event.userId)
    }
}
