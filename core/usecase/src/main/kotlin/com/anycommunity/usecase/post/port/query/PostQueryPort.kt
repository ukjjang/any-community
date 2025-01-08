package com.anycommunity.usecase.post.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.post.port.query.model.GetPostDetailQuery
import com.anycommunity.usecase.post.port.query.model.PostDetailResult
import com.anycommunity.usecase.post.port.query.model.SearchPostQuery
import com.anycommunity.usecase.post.port.query.model.SearchedPostResult
import com.anycommunity.usecase.post.usecase.query.GetPostDetailUsecase
import com.anycommunity.usecase.post.usecase.query.SearchPostUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface PostQueryPort {
    infix fun ask(query: GetPostDetailQuery): PostDetailResult

    infix fun ask(query: SearchPostQuery): CustomPage<SearchedPostResult>
}

@Service
internal class PostQueryPortImpl(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val searchPostUsecase: SearchPostUsecase,
) : PostQueryPort {
    override fun ask(query: GetPostDetailQuery) = getPostDetailUsecase(query)

    override fun ask(query: SearchPostQuery) = searchPostUsecase(query)
}
