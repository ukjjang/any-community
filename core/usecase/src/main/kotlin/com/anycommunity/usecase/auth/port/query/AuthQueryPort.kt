package com.anycommunity.usecase.auth.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.auth.port.query.model.LoginQuery
import com.anycommunity.usecase.auth.port.query.model.LoginResult
import com.anycommunity.usecase.auth.usecase.query.LoginUsecase

sealed interface AuthQueryPort {
    infix fun ask(query: LoginQuery): LoginResult
}

@Service
internal class AuthQueryPortImpl(
    private val loginUsecase: LoginUsecase,
) : AuthQueryPort {
    override fun ask(query: LoginQuery) = loginUsecase(query)
}
