package com.jinuk.toy.domain.post

import java.time.LocalDateTime
import com.jinuk.toy.common.util.domainhelper.BaseDomain
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.common.value.post.PostCategory
import com.jinuk.toy.common.value.post.PostTitle
import com.jinuk.toy.infra.rdb.post.entity.PostEntity

data class Post internal constructor(
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

    override fun hashCode() = _id?.hashCode() ?: 0

    fun update(title: PostTitle, category: PostCategory, content: String) = this.copy(
        title = title,
        category = category,
        content = content,
    )

    fun updateCommentCount(countOperation: CountOperation) = this.copy(
        commentCount = commentCount + countOperation.delta,
    )

    fun updateLikeCount(countOperation: CountOperation) = this.copy(likeCount = likeCount + countOperation.delta)

    companion object {
        fun create(userId: Long, title: PostTitle, category: PostCategory, content: String) = Post(
            userId = userId,
            title = title,
            category = category,
            content = content,
        )
    }
}

internal fun PostEntity.toModel() = Post(
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

internal fun Post.toEntity() = PostEntity(
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
