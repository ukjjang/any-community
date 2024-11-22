package com.anycommunity.mvcapi.post.request

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.usecase.comment.command.usecase.UpdateCommentCommand

@Schema(description = "댓글 수정 요청")
data class CommentUpdateRequest(
    @field:Schema(description = "수정할 댓글 내용", example = "이걸로 변경합니다!")
    val content: String,
) {
    fun toCommand(id: Long, postId: Long, userId: Long) = UpdateCommentCommand(
        id = id,
        userId = userId,
        postId = postId,
        content = content,
    )
}
