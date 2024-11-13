package com.jinuk.toy.mvcapi.post.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import com.jinuk.toy.applicaiton.post.query.usecase.SearchedPostResult
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.common.value.user.Username

@Schema(description = "게시글 검색 응답")
class PostSearchResponse(
    @field:Schema(description = "게시글 id", example = "1")
    val id: Long,
    @field:Schema(description = "게시글 제목", example = "제목입니다!")
    val title: PostTitle,
    @field:Schema(description = "게시글 카테고리 정보")
    val category: PostCategoryResponse,
    @field:Schema(description = "작성자 이름", example = "ukjjang")
    val userName: Username,
    @field:Schema(description = "댓글 개수", example = "10")
    val commentCount: Long,
    @field:Schema(description = "게시글 생성 시간", example = "2024-01-01T12:00:00")
    val createdAt: LocalDateTime,
    @field:Schema(description = "게시글 수정 시간", example = "2024-01-02T15:30:00")
    val updatedAt: LocalDateTime,
)

fun SearchedPostResult.toResponse() =
    PostSearchResponse(
        id = id,
        title = title,
        category = PostCategoryResponse.of(category),
        userName = userName,
        commentCount = commentCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
