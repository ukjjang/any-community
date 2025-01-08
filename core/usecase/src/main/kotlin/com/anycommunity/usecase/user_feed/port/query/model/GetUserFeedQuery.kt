package com.anycommunity.usecase.user_feed.port.query.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class GetUserFeedQuery(
    val userId: Long,
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
