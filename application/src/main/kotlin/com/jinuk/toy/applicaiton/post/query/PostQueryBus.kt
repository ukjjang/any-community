package com.jinuk.toy.applicaiton.post.query

import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.post.query.result.PostDetailResult
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailQuery
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailUsecase
import com.jinuk.toy.applicaiton.post.query.usecase.SearchPostQuery
import com.jinuk.toy.applicaiton.post.query.usecase.SearchPostUsecase
import com.jinuk.toy.applicaiton.post.query.usecase.SearchedPostResult

sealed interface PostQueryBus {
    infix fun ask(query: GetPostDetailQuery): PostDetailResult

    infix fun ask(query: SearchPostQuery): Page<SearchedPostResult>
}

@Service
internal class PostQueryBusImpl(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val searchPostUsecase: SearchPostUsecase,
) : PostQueryBus {
    override fun ask(query: GetPostDetailQuery) = getPostDetailUsecase(query)

    override fun ask(query: SearchPostQuery) = searchPostUsecase(query)
}
