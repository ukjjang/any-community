package com.anycommunity.usecase.user_feed.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user_feed.port.query.model.GetUserFeedQuery
import com.anycommunity.usecase.user_feed.port.query.model.UserFeedResult
import com.anycommunity.usecase.user_feed.usecase.query.GetUserFeedUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface UserFeedQueryPort {
    infix fun ask(query: GetUserFeedQuery): CustomPage<UserFeedResult>
}

@Service
internal class UserFeedQueryPortImpl(
    private val getUserFeedUsecase: GetUserFeedUsecase,
) : UserFeedQueryPort {
    override fun ask(query: GetUserFeedQuery) = getUserFeedUsecase(query)
}
