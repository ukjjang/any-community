package com.anycommunity.usecase.follow.usecase.query

import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.follow.Follow
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.usecase.follow.port.query.model.GetFollowerQuery
import com.anycommunity.usecase.follow.port.query.model.GetFollowerResult
import com.anycommunity.util.custompage.CustomPage
import com.anycommunity.util.custompage.toCustomPage

@Service
class GetFollowerUsecase(
    private val followQueryService: FollowQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetFollowerQuery) = cached(
        key = "GetFollowerUsecase:${query.hashCode()}",
    ) {
        followQueryService
            .search(
                followingUserId = query.followingUserId,
                pageable = query.pageable(),
                sortType = query.followSearchSortType,
            ).let { createPage(it) }
    }

    private fun createPage(pages: PageImpl<Follow>): CustomPage<GetFollowerResult> {
        val followerUserIds = pages.content.map { it.followerUserId }
        val userMap = userQueryService.findByIdIn(followerUserIds).associateBy { it.id }

        val content = pages.content.map { follow ->
            GetFollowerResult(
                id = follow.followerUserId,
                username = userMap.getValue(follow.followerUserId).username,
                createdAt = follow.createdAt,
            )
        }

        return pages.toCustomPage(content)
    }
}
