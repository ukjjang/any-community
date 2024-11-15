package com.jinuk.toy.applicaiton.follow.query.usecase

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.jinuk.toy.common.util.custompage.CustomPage
import com.jinuk.toy.common.util.custompage.toCustomPage
import com.jinuk.toy.common.value.follow.FollowSearchSortType
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.follow.Follow
import com.jinuk.toy.domain.follow.service.FollowQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.infra.redis.cache.cached

@Service
class GetFollowingUsecase(
    private val followQueryService: FollowQueryService,
    private val userQueryService: UserQueryService,
) {
    operator fun invoke(query: GetFollowingQuery) = cached(
        key = "GetFollowingUsecase:${query.hashCode()}",
        transactional = true,
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
