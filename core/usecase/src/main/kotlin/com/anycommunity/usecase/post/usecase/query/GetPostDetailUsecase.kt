package com.anycommunity.usecase.post.usecase.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeQueryService
import com.anycommunity.domain.post.service.PostQueryService
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.post.port.query.model.GetPostDetailQuery
import com.anycommunity.usecase.post.port.query.model.PostDetailResult

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
