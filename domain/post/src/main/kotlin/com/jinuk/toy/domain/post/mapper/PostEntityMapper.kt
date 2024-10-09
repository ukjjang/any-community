package com.jinuk.toy.domain.post.mapper

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.infra.rdb.post.entity.PostEntity

internal object PostEntityMapper {
    fun PostEntity.toModel() = Post(
        id = id,
        title = PostTitle(title),
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    fun Post.toEntity() = PostEntity(
        id = id,
        title = title.value,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
