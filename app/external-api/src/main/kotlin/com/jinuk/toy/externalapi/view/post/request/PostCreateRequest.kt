package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.applicaiton.post.command.dto.PostCreateCommandDto
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "게시글 생성 요청")
data class PostCreateRequest(
    @Schema(description = "게시물 제목")
    val title: String,
)

internal fun PostCreateRequest.toCommand() = PostCreateCommandDto(title)
