package com.jinuk.toy.mvcapi.post.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle

@Schema(description = "게시글 응답")
data class PostResponse(
    @field:Schema(description = "게시글 id", example = "1")
    val id: Long?,
    @field:Schema(description = "게시글 제목", example = "제목입니다!")
    val title: PostTitle,
    @field:Schema(description = "게시글 카테고리 정보")
    val category: PostCategoryResponse,
    @field:Schema(description = "게시글 내용", example = "안녕하세요!")
    val content: String,
    @field:Schema(description = "게시글 생성 시간", example = "2024-01-01T12:00:00")
    val createdAt: LocalDateTime,
    @field:Schema(description = "게시글 수정 시간", example = "2024-01-02T15:30:00")
    val updatedAt: LocalDateTime,
)

fun Post.toResponse() =
    PostResponse(
        id = id,
        title = title,
        category = PostCategoryResponse.of(category),
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )