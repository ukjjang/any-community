package com.jinuk.toy.domain.comment

import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity
import com.jinuk.toy.util.domainhelper.BaseDomain
import java.time.LocalDateTime

data class Comment(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),

    val userId: Long,
    val postId: Long,
    val parentCommentId: Long? = null,
    val content: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?) = super.equals(other)
    override fun hashCode() = super.hashCode()
}

internal fun CommentEntity.toModel() = Comment(
    _id = id,
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun Comment.toEntity() = CommentEntity(
    id = _id,
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
