package com.jinuk.toy.externalapi.view.post.response

import com.jinuk.toy.domain.post.Post
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "게시글 생성 응답")
data class PostCreateResponse(

    @Schema(description = "게시물 id")
    val id: Long?,

    @Schema(description = "게시물 제목")
    val title: String,

    @Schema(description = "생성일자", example = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,

    @Schema(description = "수정일자", example = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime,
)

fun Post.toResponse() = PostCreateResponse(
    id = id,
    title = title,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
