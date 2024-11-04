package com.jinuk.toy.applicaiton.post.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.user.service.UserQueryService

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

        val likeCount = likeQueryService.countByTarget(likeTarget)

        return PostDetailResult.from(
            post = post,
            writer = writer,
            isViewerLike = isViewerLike,
            likeCount = likeCount,
            commentCount = post.commentCount,
        )
    }
}

data class GetPostDetailQuery(
    val id: Long,
    val viewerId: Long? = null,
)
