package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.domain.comment.service.CommentQueryService
import com.jinuk.toy.domain.post.service.PostQueryService
import com.jinuk.toy.domain.user.service.UserQueryService
import org.springframework.stereotype.Service

@Service
class GetPostDetailUsecase(
    private val postQueryService: PostQueryService,
    private val userQueryService: UserQueryService,
    private val commentQueryService: CommentQueryService,
) {
    operator fun invoke(query: GetPostDetailQuery): PostDetailResult {
        val post = postQueryService.getById(query.id)
        val comments = commentQueryService.findByPostId(post.id)
        val users = userQueryService.findByIdIn(listOf(post.userId) + comments.map { it.userId })
        return PostDetailResult.from(post, comments, users)
    }
}

data class GetPostDetailQuery(val id: Long)
