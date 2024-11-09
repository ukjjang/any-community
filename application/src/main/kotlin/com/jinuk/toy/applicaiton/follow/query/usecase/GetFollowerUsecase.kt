package com.jinuk.toy.applicaiton.follow.query.usecase

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.constant.follow.FollowSearchSortType
import com.jinuk.toy.domain.user.service.FollowQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.domain.user.value.Username
import com.jinuk.toy.infra.redis.cache.cached
import com.jinuk.toy.util.custompage.CustomPage

@Service
class GetFollowerUsecase(
    private val followQueryService: FollowQueryService,
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetFollowerQuery) =
        cached(
            key = "GetFollowerUsecase.invoke.${query.hashCode()}",
        ) {
            val followPage =
                followQueryService.findByFollowingUserId(query.followingUserId, query.pageable())

            val content =
                followPage.content
                    .map { it.followerUserId }
                    .let { followerUserId ->
                        userQueryService.findByIdIn(followerUserId)
                            .associateBy { it.id }
                            .let { userMap ->
                                followerUserId.mapNotNull { userMap[it] }
                            }
                    }
                    .map { user ->
                        GetFollowerResult(
                            id = user.id,
                            username = user.username,
                        )
                    }

            CustomPage(content, followPage.pageable, followPage.totalElements)
        }
}

data class GetFollowerQuery(
    val followingUserId: Long,
    val page: Int,
    val size: Int,
    val followSearchSortType: FollowSearchSortType,
) {
    private fun sort(): Sort =
        when (followSearchSortType) {
            FollowSearchSortType.RECENTLY -> Sort.by(Sort.Order.desc("id"))
            FollowSearchSortType.OLDEST -> Sort.by(Sort.Order.asc("id"))
        }

    fun pageable(): Pageable = PageRequest.of(page - 1, size, sort())
}

data class GetFollowerResult(
    val id: Long,
    val username: Username,
)
