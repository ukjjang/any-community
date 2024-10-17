package com.jinuk.toy.applicaiton.post.query.result

import com.jinuk.toy.domain.comment.Comment
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
    val comments: List<CommentDetail>,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
) {

    data class CommentDetail(
        val id: Long?,
        val userId: Long,
        val username: Username,
        val content: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
    ) {
        companion object {
            fun from(comment: Comment, username: Username) = with(comment) {
                CommentDetail(
                    id = id,
                    userId = userId,
                    username = username,
                    content = content,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
        }
    }

    companion object {
        fun from(post: Post, comments: List<Comment>, users: List<User>): PostDetailResult {
            val usernameMap = users.associate { it.id to it.username }
            val commentDetails = comments.mapNotNull { comment ->
                val commentUsername = usernameMap[comment.userId] ?: return@mapNotNull null
                CommentDetail.from(comment, commentUsername)
            }

            return with(post) {
                PostDetailResult(
                    id = id,
                    userId = userId,
                    username = usernameMap.getValue(userId),
                    title = title,
                    content = content,
                    comments = commentDetails,
                    createdAt = createdAt,
                    updatedAt = updatedAt,
                )
            }
        }
    }
}


