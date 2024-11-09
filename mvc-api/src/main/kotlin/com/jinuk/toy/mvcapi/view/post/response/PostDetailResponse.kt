package com.jinuk.toy.mvcapi.view.post.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import com.jinuk.toy.applicaiton.post.query.usecase.PostDetailResult
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.value.Username

@Schema(description = "게시글 상세 응답")
data class PostDetailResponse(
    @field:Schema(description = "게시글 id", example = "1")
    val id: Long?,
    @field:Schema(description = "작성자 id", example = "123")
    val userId: Long,
    @field:Schema(description = "작성자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "게시글 제목", example = "제목입니다!")
    val title: PostTitle,
    @field:Schema(description = "게시글 카테고리 정보")
    val category: PostCategoryResponse,
    @field:Schema(description = "게시글 내용", example = "안녕하세요!")
    val content: String,
    @field:Schema(description = "현재 사용자가 게시글을 좋아요했는지 여부", example = "true")
    val isViewerLike: Boolean,
    @field:Schema(description = "좋아요 개수", example = "42")
    val likeCount: Long,
    @field:Schema(description = "댓글 개수", example = "10")
    val commentCount: Long,
    @field:Schema(description = "게시글 생성 시간", example = "2024-01-01T12:00:00")
    val createdAt: LocalDateTime,
    @field:Schema(description = "게시글 수정 시간", example = "2024-01-02T15:30:00")
    val updatedAt: LocalDateTime,
)

internal fun PostDetailResult.toResponse() =
    with(this) {
        PostDetailResponse(
            id = id,
            userId = userId,
            username = username,
            title = title,
            category = PostCategoryResponse.of(category),
            content = content,
            isViewerLike = isViewerLike,
            likeCount = likeCount,
            commentCount = commentCount,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
