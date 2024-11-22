package com.anycommunity.usecase.post.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.anycommunity.definition.like.LikeType
import com.anycommunity.definition.post.PostCategory
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeQueryService
import com.anycommunity.domain.post.Post
import com.anycommunity.domain.post.service.PostQueryService
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.service.UserQueryService

@Service
class GetPostDetailUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
    private val likeQueryService: LikeQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetPostDetailQuery): PostDetailResult {
        val post = postQueryService.getById(query.id)
        val writer = userQueryService.getById(post.userId)

        val likeTarget = LikeTarget.from(LikeType.POST, post.id)

        val isViewerLike = query.viewerId?.let { viewerId ->
            likeQueryService.existsByUserIdAndTarget(viewerId, likeTarget)
        } ?: false

        return PostDetailResult.from(
            post = post,
            writer = writer,
            isViewerLike = isViewerLike,
            commentCount = post.commentCount,
        )
    }
}

data class GetPostDetailQuery(
    val id: Long,
    val viewerId: Long? = null,
)

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
