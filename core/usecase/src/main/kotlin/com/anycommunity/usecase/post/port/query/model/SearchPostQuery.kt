package com.anycommunity.usecase.post.port.query.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import com.anycommunity.definition.post.PostSearchSortType

data class SearchPostQuery(
    val keyword: String?,
    val page: Int,
    val size: Int,
    val postSearchSortType: PostSearchSortType,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
