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
        val content =
            pages.content
                .map { it.followerUserId }
                .let { followerUserId ->
                    userQueryService
                        .findByIdIn(followerUserId)
                        .associateBy { it.id }
                        .let { userMap ->
                            followerUserId.mapNotNull { userMap[it] }
                        }
                }.map { user ->
                    GetFollowerResult(
                        id = user.id,
                        username = user.username,
                    )
                }

        return pages.toCustomPage(content)
    }
}

data class GetFollowerQuery(
    val followingUserId: Long,
    val page: Int,
    val size: Int,
    val followSearchSortType: FollowSearchSortType,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}

data class GetFollowerResult(
    val id: Long,
    val username: Username,
)
