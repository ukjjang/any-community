package com.anycommunity.domain.post

import java.time.LocalDateTime
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.shared.BaseDomain
import com.anycommunity.infra.mysql.post.entity.PostEntity

@ConsistentCopyVisibility
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

    internal fun update(info: PostUpdateInfo): Post {
        require(userId == info.userId) { "작성자만 게시글을 수정할 수 있습니다." }
        return this.copy(
            title = info.title,
            category = info.category,
            content = info.content,
        )
    }

    internal fun updateCommentCount(countOperation: CountOperation) = this.copy(
        commentCount = commentCount + countOperation.delta,
    )

    internal fun updateLikeCount(countOperation: CountOperation) =
        this.copy(likeCount = likeCount + countOperation.delta)

    companion object {
        internal fun create(info: PostCreateInfo) = Post(
            userId = info.userId,
            title = info.title,
            category = info.category,
            content = info.content,
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
