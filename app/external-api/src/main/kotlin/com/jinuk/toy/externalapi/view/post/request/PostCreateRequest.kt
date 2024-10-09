package com.jinuk.toy.externalapi.view.post.request

import com.jinuk.toy.domain.post.service.command.PostCreateCommand
import com.jinuk.toy.domain.post.value.PostTitle
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "게시글 생성 요청")
data class PostCreateRequest(
    @Schema(description = "게시물 제목")
    val title: PostTitle,
)

internal fun PostCreateRequest.toCommand() = PostCreateCommand(title)
