package com.anycommunity.usecase.comment.port.query.model

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

data class GetCommentPageQuery(
    val postId: Long,
    val viewerId: Long? = null,
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}
