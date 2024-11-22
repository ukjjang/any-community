package com.anycommunity.usecase.auth.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.UserCredentials
import com.anycommunity.domain.user.service.UserAuthService

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
) {
    fun toUserCredentials() = UserCredentials(
        username = username,
        password = password,
    )
}

data class LoginResult(
    val token: String,
)
