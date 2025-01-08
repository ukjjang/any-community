package com.anycommunity.usecase.follow.port.query.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import com.anycommunity.definition.follow.FollowSearchSortType

data class GetFollowingQuery(
    val followerUserId: Long,
    val page: Int,
    val size: Int,
    val followSearchSortType: FollowSearchSortType,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
