package com.jinuk.toy.applicaiton.follow.query.usecase

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.constant.follow.FollowSearchSortType
import com.jinuk.toy.domain.user.Follow
import com.jinuk.toy.domain.user.service.FollowQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.infra.redis.cache.cached
import com.jinuk.toy.util.custompage.CustomPage

@Service
class GetFollowingUsecase(
    private val followQueryService: FollowQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetFollowingQuery) =
        cached(
            key = "GetFollowingUsecase.invoke.${query.hashCode()}",
        ) {
            followQueryService
                .search(
                    followerUserId = query.followerUserId,
                    pageable = query.pageable(),
                    sortType = query.followSearchSortType,
                ).let { createPage(it.content, it.pageable, it.totalElements) }
        }

    private fun createPage(
        follows: List<Follow>,
        pageable: Pageable,
        totalSize: Long,
    ): CustomPage<GetFollowingResult> {
        val content =
            follows
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

        return CustomPage(content, pageable, totalSize)
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