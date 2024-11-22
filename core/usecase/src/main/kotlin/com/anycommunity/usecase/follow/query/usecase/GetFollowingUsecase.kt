package com.anycommunity.usecase.follow.query.usecase

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.follow.FollowSearchSortType
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.follow.Follow
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
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
        val content =
            pages.content
                .map { it.followingUserId }
                .let { followingUserId ->
                    userQueryService
                        .findByIdIn(followingUserId)
                        .associateBy { it.id }
                        .let { userMap ->
                            followingUserId.mapNotNull { userMap[it] }
                        }
                }.map { user ->
                    GetFollowingResult(
                        id = user.id,
                        username = user.username,
                    )
                }

        return pages.toCustomPage(content)
    }
}

data class GetFollowingQuery(
    val followerUserId: Long,
    val page: Int,
    val size: Int,
    val followSearchSortType: FollowSearchSortType,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}

data class GetFollowingResult(
    val id: Long,
    val username: Username,
)
