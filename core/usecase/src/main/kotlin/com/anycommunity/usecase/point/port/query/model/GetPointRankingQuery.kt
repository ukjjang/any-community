package com.anycommunity.usecase.point.port.query.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class GetPointRankingQuery(
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
