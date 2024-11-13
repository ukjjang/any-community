package com.jinuk.toy.mvcapi.post.response

import io.swagger.v3.oas.annotations.media.Schema
import com.jinuk.toy.common.value.post.PostCategory

@Schema(description = "게시글 카테고리 응답")
data class PostCategoryResponse(
    @field:Schema(description = "게시글 카테고리 코드", example = "FREEDOM")
    val category: PostCategory,
    @field:Schema(description = "게시글 카테고리 라벨", example = "자유")
    val label: String,
) {
    companion object {
        fun of(postCategory: PostCategory): PostCategoryResponse =
            PostCategoryResponse(postCategory, postCategory.label)
    }
}
