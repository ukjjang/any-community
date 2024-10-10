package com.jinuk.toy.externalapi.view.post.response

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "게시글 응답")
data class PostResponse(

    @Schema(description = "게시물 id")
    val id: Long?,

    @Schema(description = "게시물 제목")
    val title: PostTitle,

    @Schema(description = "생성일자", example = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,

    @Schema(description = "수정일자", example = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime,
)

fun Post.toResponse() = PostResponse(
    id = id,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
