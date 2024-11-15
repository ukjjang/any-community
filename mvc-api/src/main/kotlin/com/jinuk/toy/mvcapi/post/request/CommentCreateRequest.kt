package com.jinuk.toy.mvcapi.post.request

import io.swagger.v3.oas.annotations.media.Schema
import com.jinuk.toy.applicaiton.comment.command.usecase.CreateCommentCommand

@Schema(description = "댓글 생성 요청")
data class CommentCreateRequest(
    @field:Schema(description = "부모 댓글 id (최상위 댓글이면 null)", example = "1")
    val parentCommentId: Long?,
    @field:Schema(description = "댓글 내용", example = "좋은 글이네요!")
    val content: String,
)

internal fun CommentCreateRequest.toCommand(postId: Long, userId: Long) = CreateCommentCommand(
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
)
