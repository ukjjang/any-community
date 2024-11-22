package com.anycommunity.usecase.auth.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.auth.query.usecase.LoginQuery
import com.anycommunity.usecase.auth.query.usecase.LoginResult
import com.anycommunity.usecase.auth.query.usecase.LoginUsecase

sealed interface AuthQueryBus {
    infix fun ask(query: LoginQuery): LoginResult
}

@Service
internal class AuthQueryBusImpl(
    private val loginUsecase: LoginUsecase,
) : AuthQueryBus {
    override fun ask(query: LoginQuery) = loginUsecase(query)
}
