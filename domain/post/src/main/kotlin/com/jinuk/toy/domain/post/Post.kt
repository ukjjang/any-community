package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.infra.rdb.post.entity.PostEntity
import java.time.LocalDateTime

data class Post(
    val id: Long? = null,
    val userId: Long,
    val title: PostTitle,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Post) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}

internal fun PostEntity.toModel() = Post(
    id = id,
    userId = userId,
    title = PostTitle(title),
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    userId = userId,
    title = title.value,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
