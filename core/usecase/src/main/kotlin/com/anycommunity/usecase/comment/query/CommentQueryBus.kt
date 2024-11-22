package com.anycommunity.usecase.comment.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.comment.query.usecase.GetCommentPageQuery
import com.anycommunity.usecase.comment.query.usecase.GetCommentPageResult
import com.anycommunity.usecase.comment.query.usecase.GetCommentPageUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface CommentQueryBus {
    infix fun ask(query: GetCommentPageQuery): CustomPage<GetCommentPageResult>
}

@Service
internal class CommentQueryBusImpl(
    private val getCommentPageUsecase: GetCommentPageUsecase,
) : CommentQueryBus {
    override fun ask(query: GetCommentPageQuery) = getCommentPageUsecase(query)
}
