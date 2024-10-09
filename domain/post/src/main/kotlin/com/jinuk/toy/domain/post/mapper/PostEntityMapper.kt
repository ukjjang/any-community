package com.jinuk.toy.domain.post.mapper

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.infra.rdb.post.entity.PostEntity

internal object PostEntityMapper {
    fun PostEntity.toModel() = Post(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

    fun Post.toEntity() = PostEntity(
        id = id,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}
