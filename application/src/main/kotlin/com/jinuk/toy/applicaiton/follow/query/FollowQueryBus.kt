package com.jinuk.toy.applicaiton.follow.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingQuery
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingResult
import com.jinuk.toy.applicaiton.follow.query.usecase.GetFollowingUsecase
import com.jinuk.toy.util.custompage.CustomPage

sealed interface FollowQueryBus {
    infix fun ask(query: GetFollowingQuery): CustomPage<GetFollowingResult>
}

@Service
internal class FollowQueryBusImpl(
    private val getFollowingUsecase: GetFollowingUsecase,
) : FollowQueryBus {
    override fun ask(query: GetFollowingQuery) = getFollowingUsecase(query)
}
