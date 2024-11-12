package com.jinuk.toy.applicaiton.follow.query.usecase

import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import com.jinuk.toy.common.define.follow.FollowSearchSortType
import com.jinuk.toy.common.define.user.Username
import com.jinuk.toy.common.util.custompage.CustomPage
import com.jinuk.toy.common.util.custompage.toCustomPage
import com.jinuk.toy.domain.follow.Follow
import com.jinuk.toy.domain.follow.service.FollowQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.infra.redis.cache.cached

@Service
class GetFollowerUsecase(
    private val followQueryService: FollowQueryService,
    private val userQueryService: UserQueryService,
) {
    operator fun invoke(query: GetFollowerQuery) =
        cached(
            key = "GetFollowerUsecase:${query.hashCode()}",
            transactional = true,
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
