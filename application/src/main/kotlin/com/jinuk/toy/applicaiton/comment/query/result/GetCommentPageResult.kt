package com.jinuk.toy.applicaiton.comment.query.result

import com.jinuk.toy.domain.user.value.Username

data class GetCommentPageResult(
    val id: Long,
    val username: Username,
    val isViewerLike: Boolean,
    val parentCommentId: Long?,
    val likeCount: Long,
    val content: String,
    val children: List<GetCommentPageResult>,
)
