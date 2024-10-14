package com.jinuk.toy.applicaiton.post.usecase

import com.jinuk.toy.applicaiton.post.query.GetPostDetailQuery
import com.jinuk.toy.domain.post.service.PostQueryService
import org.springframework.stereotype.Service

@Service
class GetPostDetailUsecase(
    private val postQueryService: PostQueryService
) {
    operator fun invoke(query: GetPostDetailQuery) = postQueryService.getById(query.id)
}
