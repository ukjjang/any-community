package com.jinuk.toy.applicaiton.auth.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.value.user.RawPassword
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService

@Service
class LoginUsecase(
    private val authService: UserAuthService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: LoginQuery) = LoginResult(authService.login(query.toUserCredentials()))
}

data class LoginQuery(
    val username: Username,
    val password: RawPassword,
)

private fun LoginQuery.toUserCredentials() = UserCredentials(
    username = username,
    password = password,
)

data class LoginResult(
    val token: String,
)
