package com.anycommunity.domain.comment

import java.time.LocalDateTime
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.shared.BaseDomain

@ConsistentCopyVisibility
data class Comment internal constructor(
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

    override fun hashCode() = _id?.hashCode() ?: 0

    internal fun update(info: CommentUpdateInfo): Comment {
        require(userId == info.userId) { "작성자만 게시글을 수정할 수 있습니다." }
        require(postId == info.postId) { "해당 댓글은 지정된 게시글에 속하지 않습니다." }
        return this.copy(content = info.content)
    }

    internal fun updateLikeCount(info: CountOperation) = this.copy(likeCount = likeCount + info.delta)

    companion object {
        internal fun create(info: CommentCreateInfo) = Comment(
            userId = info.userId,
            postId = info.postId,
            parentCommentId = info.parentCommentId,
            content = info.content,
        )
    }
}
