package com.jinuk.toy.applicaiton.comment.query

import com.jinuk.toy.applicaiton.comment.query.result.GetCommentPageResult
import com.jinuk.toy.applicaiton.comment.query.usecase.GetCommentPageQuery
import com.jinuk.toy.applicaiton.comment.query.usecase.GetCommentPageUsecase
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service

sealed interface CommentQueryBus {
    infix fun ask(query: GetCommentPageQuery): Page<GetCommentPageResult>
}

@Service
internal class CommentQueryBusImpl(
    private val getCommentPageUsecase: GetCommentPageUsecase,
) : CommentQueryBus {
    override fun ask(query: GetCommentPageQuery) = getCommentPageUsecase(query)
}
