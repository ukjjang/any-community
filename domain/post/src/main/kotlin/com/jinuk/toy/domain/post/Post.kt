package com.jinuk.toy.domain.post

import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.infra.rdb.post.entity.PostEntity
import com.jinuk.toy.util.domainhelper.BaseDomain
import java.time.LocalDateTime

data class Post(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),

    val userId: Long,
    val title: PostTitle,
    val content: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()
}

internal fun PostEntity.toModel() = Post(
    _id = id,
    userId = userId,
    title = PostTitle(title),
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun Post.toEntity() = PostEntity(
    id = _id,
    userId = userId,
    title = title.value,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
