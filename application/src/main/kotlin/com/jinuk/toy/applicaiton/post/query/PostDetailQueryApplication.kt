package com.jinuk.toy.applicaiton.post.query

import com.jinuk.toy.domain.post.service.PostQueryService
import org.springframework.stereotype.Service

@Service
class PostDetailQueryApplication(
    private val postQueryService: PostQueryService
) {
    operator fun invoke(id: Long) = postQueryService.getById(id)
}
