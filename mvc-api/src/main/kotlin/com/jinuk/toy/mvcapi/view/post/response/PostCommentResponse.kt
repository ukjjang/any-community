package com.jinuk.toy.mvcapi.view.post.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import com.jinuk.toy.applicaiton.comment.query.usecase.GetCommentPageResult
import com.jinuk.toy.domain.user.value.Username

@Schema(description = "게시글 댓글 응답")
data class PostCommentResponse(
    @field:Schema(description = "댓글 id", example = "1")
    val id: Long,
    @field:Schema(description = "작성자 유저 id", example = "3")
    val username: Username,
    @field:Schema(description = "현재 사용자가 게시글을 좋아요했는지 여부", example = "true")
    val isViewerLike: Boolean,
    @field:Schema(description = "부모 댓글 id", example = "1")
    val parentCommentId: Long?,
    @field:Schema(description = "좋아요 개수", example = "27")
    val likeCount: Long,
    @field:Schema(description = "댓글 내용", example = "댓글입니다.")
    val content: String,
    @field:Schema(description = "자식 댓글 리스트")
    val children: List<PostCommentResponse>,
    @field:Schema(description = "댓글 작성 시간", example = "2024-01-01T12:00:00")
    val createdAt: LocalDateTime,
    @field:Schema(description = "댓글 수정 시간", example = "2024-01-01T12:00:00")
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(result: GetCommentPageResult): PostCommentResponse =
            with(result) {
                return PostCommentResponse(
                    id = id,
                    username = username,
                    isViewerLike = isViewerLike,
                    parentCommentId = parentCommentId,
                    likeCount = likeCount,
                    content = content,
                    children = children.map { from(it) },
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
    }
}
