package com.jinuk.toy.applicaiton.user.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.user.query.usecase.GetUserInfoQuery
import com.jinuk.toy.applicaiton.user.query.usecase.GetUserInfoResult
import com.jinuk.toy.applicaiton.user.query.usecase.GetUserInfoUsecase

sealed interface UserQueryBus {
    infix fun ask(query: GetUserInfoQuery): GetUserInfoResult
}

@Service
internal class UserQueryBusImpl(
    private val getUserInfoUsecase: GetUserInfoUsecase,
) : UserQueryBus {
    override fun ask(query: GetUserInfoQuery) = getUserInfoUsecase(query)
}
