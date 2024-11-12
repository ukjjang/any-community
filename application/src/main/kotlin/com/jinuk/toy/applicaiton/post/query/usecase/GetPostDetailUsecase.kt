package com.jinuk.toy.applicaiton.post.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import com.jinuk.toy.common.define.like.LikeType
import com.jinuk.toy.common.define.post.PostCategory
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.domain.post.Post
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.post.value.PostTitle
import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.domain.user.value.Username

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

        val isViewerLike =
            query.viewerId?.let { viewerId ->
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
        fun from(
            post: Post,
            writer: User,
            isViewerLike: Boolean,
            commentCount: Long,
        ): PostDetailResult =
            with(post) {
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
