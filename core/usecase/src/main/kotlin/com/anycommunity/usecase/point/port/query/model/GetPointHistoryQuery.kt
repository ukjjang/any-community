package com.anycommunity.usecase.point.port.query.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class GetPointHistoryQuery(
    val userId: Long,
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
