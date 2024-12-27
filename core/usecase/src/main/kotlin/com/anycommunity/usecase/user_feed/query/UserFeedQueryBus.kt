package com.anycommunity.usecase.user_feed.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user_feed.query.usecase.GetUserFeedQuery
import com.anycommunity.usecase.user_feed.query.usecase.GetUserFeedUsecase
import com.anycommunity.usecase.user_feed.query.usecase.UserFeedResult
import com.anycommunity.util.custompage.CustomPage

sealed interface UserFeedQueryBus {
    infix fun ask(query: GetUserFeedQuery): CustomPage<UserFeedResult>
}

@Service
internal class UserFeedQueryBusImpl(
    private val getUserFeedUsecase: GetUserFeedUsecase,
) : UserFeedQueryBus {
    override fun ask(query: GetUserFeedQuery) = getUserFeedUsecase(query)
}
