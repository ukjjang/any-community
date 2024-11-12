package com.jinuk.toy.mvcapi.post.request

import io.swagger.v3.oas.annotations.media.Schema
import com.jinuk.toy.applicaiton.post.command.usecase.UpdatePostCommand
import com.jinuk.toy.constant.post.PostCategory
import com.jinuk.toy.domain.post.value.PostTitle

@Schema(description = "게시글 수정 요청")
data class PostUpdateRequest(
    @field:Schema(description = "변경할 게시글 제목", example = "제목1234")
    val title: PostTitle,
    @field:Schema(description = "변경할 카테고리", example = "FREEDOM")
    val category: PostCategory = PostCategory.ETC,
    @field:Schema(description = "변경할 게시글 내용", example = "안녕하세요~!")
    val content: String,
)

internal fun PostUpdateRequest.toCommand(
    userId: Long,
    id: Long,
) = UpdatePostCommand(
    userId = userId,
    id = id,
    title = title,
    category = category,
    content = content,
)
