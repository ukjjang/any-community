package com.jinuk.toy.domain.post

import java.time.LocalDateTime
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.constant.global.CountOperation
import com.jinuk.toy.constant.post.PostCategory
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.infra.rdb.post.entity.PostEntity

data class Post(
    override val _id: Long? = null,
    override val createdAt: LocalDateTime = LocalDateTime.now(),
    override val updatedAt: LocalDateTime = LocalDateTime.now(),
    val userId: Long,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
    val commentCount: Long = 0L,
    val likeCount: Long = 0L,
) : BaseDomain(_id, createdAt, updatedAt) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Post) return false
        if (_id != other._id) return false
        return true
    }

    override fun hashCode(): Int {
        return _id?.hashCode() ?: 0
    }

    fun updateCommentCount(countOperation: CountOperation) =
        this.copy(
            commentCount = commentCount + countOperation.delta,
        )

    fun updateLikeCount(countOperation: CountOperation) = this.copy(likeCount = likeCount + countOperation.delta)
}

internal fun PostEntity.toModel() =
    Post(
        _id = id,
        userId = userId,
        title = PostTitle(title),
        category = category,
        content = content,
        commentCount = commentCount,
        likeCount = likeCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

internal fun Post.toEntity() =
    PostEntity(
        id = _id,
        userId = userId,
        title = title.value,
        category = category,
        content = content,
        commentCount = commentCount,
        likeCount = likeCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
