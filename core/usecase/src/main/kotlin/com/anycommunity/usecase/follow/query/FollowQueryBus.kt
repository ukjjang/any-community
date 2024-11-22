package com.anycommunity.usecase.follow.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.follow.query.usecase.GetFollowerQuery
import com.anycommunity.usecase.follow.query.usecase.GetFollowerResult
import com.anycommunity.usecase.follow.query.usecase.GetFollowerUsecase
import com.anycommunity.usecase.follow.query.usecase.GetFollowingQuery
import com.anycommunity.usecase.follow.query.usecase.GetFollowingResult
import com.anycommunity.usecase.follow.query.usecase.GetFollowingUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface FollowQueryBus {
    infix fun ask(query: GetFollowingQuery): CustomPage<GetFollowingResult>

    infix fun ask(query: GetFollowerQuery): CustomPage<GetFollowerResult>
}

@Service
internal class FollowQueryBusImpl(
    private val getFollowingUsecase: GetFollowingUsecase,
    private val getFollowerUsecase: GetFollowerUsecase,
) : FollowQueryBus {
    override fun ask(query: GetFollowingQuery) = getFollowingUsecase(query)

    override fun ask(query: GetFollowerQuery) = getFollowerUsecase(query)
}
