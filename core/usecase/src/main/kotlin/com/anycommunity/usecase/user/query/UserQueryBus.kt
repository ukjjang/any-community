package com.anycommunity.usecase.user.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user.query.usecase.GetUserInfoQuery
import com.anycommunity.usecase.user.query.usecase.GetUserInfoResult
import com.anycommunity.usecase.user.query.usecase.GetUserInfoUsecase

sealed interface UserQueryBus {
    infix fun ask(query: GetUserInfoQuery): GetUserInfoResult
}

@Service
internal class UserQueryBusImpl(
    private val getUserInfoUsecase: GetUserInfoUsecase,
) : UserQueryBus {
    override fun ask(query: GetUserInfoQuery) = getUserInfoUsecase(query)
}
