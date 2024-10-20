package com.jinuk.toy.applicaiton.post.query.result

import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.value.Username
import java.time.LocalDateTime

data class PostDetailResult(
    val id: Long?,
    val userId: Long,
    val username: Username,
    val title: PostTitle,
    val content: String,
    val isViewerLike: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {
    companion object {
        fun from(
            post: Post,
            writer: User,
            isViewerLike: Boolean,
        ): PostDetailResult {
            return with(post) {
                PostDetailResult(
                    id = id,
                    userId = userId,
                    username = writer.username,
                    title = title,
                    isViewerLike = isViewerLike,
                    content = content,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
        }
    }
}
