package com.jinuk.toy.mvcapi.view.post.response

import com.jinuk.toy.constant.post.PostCategory

data class PostCategoryResponse(
    val category: PostCategory,
    val label: String,
) {
    companion object {
        fun of(postCategory: PostCategory): PostCategoryResponse =
            PostCategoryResponse(postCategory, postCategory.label)
    }
}
