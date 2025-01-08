package com.anycommunity.usecase.post.port.query.model

import java.time.LocalDateTime
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.user.User

data class PostDetailResult(
    val id: Long?,
    val userId: Long,
    val username: Username,
    val title: PostTitle,
    val category: PostCategory,
    val content: String,
    val isViewerLike: Boolean,
    val likeCount: Long,
    val commentCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(post: Post, writer: User, isViewerLike: Boolean, commentCount: Long): PostDetailResult = with(post) {
            PostDetailResult(
                id = id,
                userId = userId,
                username = writer.username,
                title = title,
                category = category,
                isViewerLike = isViewerLike,
                likeCount = likeCount,
                commentCount = commentCount,
                content = content,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}
