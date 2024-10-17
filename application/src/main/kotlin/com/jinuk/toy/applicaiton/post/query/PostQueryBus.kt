package com.jinuk.toy.applicaiton.post.query

import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailQuery
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailUsecase
import com.jinuk.toy.domain.post.Post
import org.springframework.stereotype.Service

sealed interface PostQueryBus {
    infix fun query(query: GetPostDetailQuery): PostDetailResult
}

@Service
internal class PostQueryBusImpl(
    private val getPostDetailUsecase: GetPostDetailUsecase
) : PostQueryBus {

    override fun query(query: GetPostDetailQuery) = getPostDetailUsecase(query)
}
