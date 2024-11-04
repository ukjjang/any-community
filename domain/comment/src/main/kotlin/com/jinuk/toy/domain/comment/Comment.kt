package com.jinuk.toy.domain.comment

import java.time.LocalDateTime
import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity
import com.jinuk.toy.util.domainhelper.BaseDomain

data class Comment(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long? = null,
    val content: String,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Comment) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode(): Int {
        return _id?.hashCode() ?: 0
    }
}

internal fun CommentEntity.toModel() =
    Comment(
        _id = id,
        userId = userId,
        postId = postId,
        parentCommentId = parentCommentId,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun Comment.toEntity() =
    CommentEntity(
        id = _id,
        userId = userId,
        postId = postId,
        parentCommentId = parentCommentId,
        content = content,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
