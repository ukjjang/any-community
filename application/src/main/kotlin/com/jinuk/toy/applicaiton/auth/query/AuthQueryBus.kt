package com.jinuk.toy.applicaiton.auth.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.auth.query.result.LoginResult
import com.jinuk.toy.applicaiton.auth.query.usecase.LoginQuery
import com.jinuk.toy.applicaiton.auth.query.usecase.LoginUsecase

sealed interface AuthQueryBus {
    infix fun ask(query: LoginQuery): LoginResult
}

@Service
internal class AuthQueryBusImpl(
    private val loginUsecase: LoginUsecase,
) : AuthQueryBus {
    override fun ask(query: LoginQuery) = loginUsecase(query)
}
