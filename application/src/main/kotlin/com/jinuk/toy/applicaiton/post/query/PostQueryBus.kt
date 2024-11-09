package com.jinuk.toy.applicaiton.post.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailQuery
import com.jinuk.toy.applicaiton.post.query.usecase.GetPostDetailUsecase
import com.jinuk.toy.applicaiton.post.query.usecase.PostDetailResult
import com.jinuk.toy.applicaiton.post.query.usecase.SearchPostQuery
import com.jinuk.toy.applicaiton.post.query.usecase.SearchPostUsecase
import com.jinuk.toy.applicaiton.post.query.usecase.SearchedPostResult
import com.jinuk.toy.util.custompage.CustomPage

sealed interface PostQueryBus {
    infix fun ask(query: GetPostDetailQuery): PostDetailResult

    infix fun ask(query: SearchPostQuery): CustomPage<SearchedPostResult>
}

@Service
internal class PostQueryBusImpl(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val searchPostUsecase: SearchPostUsecase,
) : PostQueryBus {
    override fun ask(query: GetPostDetailQuery) = getPostDetailUsecase(query)

    override fun ask(query: SearchPostQuery) = searchPostUsecase(query)
}
