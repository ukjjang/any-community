package com.anycommunity.usecase.user.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.user.port.query.model.GetUserInfoQuery
import com.anycommunity.usecase.user.port.query.model.GetUserInfoResult
import com.anycommunity.usecase.user.usecase.query.GetUserInfoUsecase

sealed interface UserQueryPort {
    infix fun ask(query: GetUserInfoQuery): GetUserInfoResult
}

@Service
internal class UserQueryPortImpl(
    private val getUserInfoUsecase: GetUserInfoUsecase,
) : UserQueryPort {
    override fun ask(query: GetUserInfoQuery) = getUserInfoUsecase(query)
}
