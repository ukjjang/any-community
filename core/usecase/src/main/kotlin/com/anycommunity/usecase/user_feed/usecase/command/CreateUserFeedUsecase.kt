package com.anycommunity.usecase.user_feed.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.user_feed.UserFeedCreateInfo
import com.anycommunity.domain.user_feed.service.UserFeedCommandService
import com.anycommunity.usecase.user_feed.port.command.model.CreateUserFeedCommand

@Service
class CreateUserFeedUsecase(
    private val userFeedCommandService: UserFeedCommandService,
    private val followQueryService: FollowQueryService,
) {
    @Transactional
    operator fun invoke(command: CreateUserFeedCommand) {
        val followerUserIds = followQueryService.findByFollowingUserId(command.userId).map { it.followerUserId }
        val createInfo = UserFeedCreateInfo(followerUserIds, command.postId, command.userId)
        userFeedCommandService.create(createInfo)
    }
}
