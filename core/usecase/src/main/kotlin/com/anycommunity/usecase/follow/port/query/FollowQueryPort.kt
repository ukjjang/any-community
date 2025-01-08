package com.anycommunity.usecase.follow.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.follow.port.query.model.GetFollowerQuery
import com.anycommunity.usecase.follow.port.query.model.GetFollowerResult
import com.anycommunity.usecase.follow.port.query.model.GetFollowingQuery
import com.anycommunity.usecase.follow.port.query.model.GetFollowingResult
import com.anycommunity.usecase.follow.usecase.query.GetFollowerUsecase
import com.anycommunity.usecase.follow.usecase.query.GetFollowingUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface FollowQueryPort {
    infix fun ask(query: GetFollowingQuery): CustomPage<GetFollowingResult>

    infix fun ask(query: GetFollowerQuery): CustomPage<GetFollowerResult>
}

@Service
internal class FollowQueryPortImpl(
    private val getFollowingUsecase: GetFollowingUsecase,
    private val getFollowerUsecase: GetFollowerUsecase,
) : FollowQueryPort {
    override fun ask(query: GetFollowingQuery) = getFollowingUsecase(query)

    override fun ask(query: GetFollowerQuery) = getFollowerUsecase(query)
}
