package com.jinuk.toy.domain.post

import java.time.LocalDateTime
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.infra.rdb.post.entity.PostEntity
import com.jinuk.toy.util.domainhelper.BaseDomain

data class Post(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val title: PostTitle,
    val content: String,
    val commentCount: Long = 0L,
) : BaseDomain(_id, createdAt, updatedAt) {
    fun increaseCommentCount() = this.copy(commentCount = commentCount + 1)

    fun decreaseCommentCount() = this.copy(commentCount = commentCount - 1)
}

internal fun PostEntity.toModel() =
    Post(
        _id = id,
        userId = userId,
        title = PostTitle(title),
        content = content,
        commentCount = commentCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun Post.toEntity() =
    PostEntity(
        id = _id,
        userId = userId,
        title = title.value,
        content = content,
        commentCount = commentCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
