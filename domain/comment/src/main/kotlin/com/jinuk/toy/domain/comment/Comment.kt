package com.jinuk.toy.domain.comment

import java.time.LocalDateTime
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.infra.rdb.comment.entity.CommentEntity

data class Comment(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val postId: Long,
    val parentCommentId: Long? = null,
    val content: String,
    val likeCount: Long = 0L,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Comment) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode(): Int = _id?.hashCode() ?: 0

    fun update(content: String) = this.copy(content = content)
    fun updateLikeCount(countOperation: CountOperation) = this.copy(likeCount = likeCount + countOperation.delta)
}

internal fun CommentEntity.toModel() = Comment(
    _id = id,
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

internal fun Comment.toEntity() = CommentEntity(
    id = _id,
    userId = userId,
    postId = postId,
    parentCommentId = parentCommentId,
    content = content,
    likeCount = likeCount,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
