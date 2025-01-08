package com.anycommunity.usecase.auth.usecase.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.user.service.UserAuthService
import com.anycommunity.usecase.auth.port.query.model.LoginQuery
import com.anycommunity.usecase.auth.port.query.model.LoginResult

@Service
class LoginUsecase(
    private val authService: UserAuthService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: LoginQuery) = LoginResult(authService.login(query.toUserCredentials()))
}
