package com.jinuk.toy.applicaiton.post

import com.jinuk.toy.applicaiton.post.query.GetPostDetailQuery
import com.jinuk.toy.applicaiton.post.usecase.GetPostDetailUsecase
import com.jinuk.toy.domain.post.Post
import org.springframework.stereotype.Service

sealed interface PostQueryBus {
    infix fun query(query: GetPostDetailQuery): Post
}

@Service
internal class PostQueryBusImpl(
    private val getPostDetailUsecase: GetPostDetailUsecase
) : PostQueryBus {

    override fun query(query: GetPostDetailQuery) = getPostDetailUsecase(query)
}
