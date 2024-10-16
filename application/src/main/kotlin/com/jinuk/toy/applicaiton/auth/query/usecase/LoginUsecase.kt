package com.jinuk.toy.applicaiton.auth.query.usecase

import com.jinuk.toy.applicaiton.auth.query.result.LoginResult
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService
import com.jinuk.toy.domain.user.value.Username
import org.springframework.stereotype.Service

@Service
class LoginUsecase(
    private val authService: UserAuthService
) {
    operator fun invoke(query: LoginQuery) = LoginResult(authService.login(query.toUserCredentials()))
}

data class LoginQuery(
    val username: Username,
    val password: String,
)

private fun LoginQuery.toUserCredentials() = UserCredentials(
    username = username,
    password = password,
)
