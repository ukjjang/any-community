package com.jinuk.toy.applicaiton.follow.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowerQuery
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowerResult
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowerUsecase
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingQuery
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingResult
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingUsecase
import com.jinuk.toy.util.custompage.CustomPage

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
