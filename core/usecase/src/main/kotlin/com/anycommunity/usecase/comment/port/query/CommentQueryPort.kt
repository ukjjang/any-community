package com.anycommunity.usecase.comment.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.comment.port.query.model.GetCommentPageQuery
import com.anycommunity.usecase.comment.port.query.model.GetCommentPageResult
import com.anycommunity.usecase.comment.usecase.query.GetCommentPageUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface CommentQueryPort {
    infix fun ask(query: GetCommentPageQuery): CustomPage<GetCommentPageResult>
}

@Service
internal class CommentQueryPortImpl(
    private val getCommentPageUsecase: GetCommentPageUsecase,
) : CommentQueryPort {
    override fun ask(query: GetCommentPageQuery) = getCommentPageUsecase(query)
}
