package com.anycommunity.usecase.follow.usecase.query

import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.follow.Follow
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.usecase.follow.port.query.model.GetFollowingQuery
import com.anycommunity.usecase.follow.port.query.model.GetFollowingResult
import com.anycommunity.util.custompage.CustomPage
import com.anycommunity.util.custompage.toCustomPage

@Service
class GetFollowingUsecase(
    private val followQueryService: FollowQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetFollowingQuery) = cached(
        key = "GetFollowingUsecase:${query.hashCode()}",
    ) {
        followQueryService
            .search(
                followerUserId = query.followerUserId,
                pageable = query.pageable(),
                sortType = query.followSearchSortType,
            ).let { createPage(it) }
    }

    private fun createPage(pages: PageImpl<Follow>): CustomPage<GetFollowingResult> {
        val followingUserIds = pages.content.map { it.followingUserId }
        val userMap = userQueryService.findByIdIn(followingUserIds).associateBy { it.id }

        val content = pages.content.map { follow ->
            GetFollowingResult(
                id = follow.followingUserId,
                username = userMap.getValue(follow.followingUserId).username,
                createdAt = follow.createdAt,
            )
        }
        return pages.toCustomPage(content)
    }
}
