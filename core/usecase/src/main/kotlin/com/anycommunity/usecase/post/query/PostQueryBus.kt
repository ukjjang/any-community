package com.anycommunity.usecase.post.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.post.query.usecase.GetPostDetailQuery
import com.anycommunity.usecase.post.query.usecase.GetPostDetailUsecase
import com.anycommunity.usecase.post.query.usecase.PostDetailResult
import com.anycommunity.usecase.post.query.usecase.SearchPostQuery
import com.anycommunity.usecase.post.query.usecase.SearchPostUsecase
import com.anycommunity.usecase.post.query.usecase.SearchedPostResult
import com.anycommunity.util.custompage.CustomPage

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
