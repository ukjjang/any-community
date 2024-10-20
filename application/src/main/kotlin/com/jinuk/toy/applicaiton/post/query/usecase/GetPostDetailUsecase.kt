package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import org.springframework.stereotype.Service

@Service
class GetPostDetailUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
    private val likeQueryService: LikeQueryService,
) {
    operator fun invoke(query: GetPostDetailQuery): PostDetailResult {
        val post = postQueryService.getById(query.id)
        val writer = userQueryService.getById(post.userId)

        val isViewerLike =
            query.viewerId?.let { viewerId ->
                likeQueryService.existsByUserIdAndTargetTypeAndTargetId(
                    viewerId,
                    LikeTarget.from(LikeType.POST, post.id),
                )
            } ?: false

        return PostDetailResult.from(post, writer, isViewerLike)
    }
}

data class GetPostDetailQuery(
    val id: Long,
    val viewerId: Long? = null,
)
