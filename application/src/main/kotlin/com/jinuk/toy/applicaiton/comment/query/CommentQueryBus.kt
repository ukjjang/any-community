package com.jinuk.toy.applicaiton.comment.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.comment.query.usecase.GetCommentPageQuery
import com.jinuk.toy.applicaiton.comment.query.usecase.GetCommentPageResult
import com.jinuk.toy.applicaiton.comment.query.usecase.GetCommentPageUsecase
import com.jinuk.toy.common.util.custompage.CustomPage

sealed interface CommentQueryBus {
    infix fun ask(query: GetCommentPageQuery): CustomPage<GetCommentPageResult>
}

@Service
internal class CommentQueryBusImpl(
    private val getCommentPageUsecase: GetCommentPageUsecase,
) : CommentQueryBus {
    override fun ask(query: GetCommentPageQuery) = getCommentPageUsecase(query)
}
