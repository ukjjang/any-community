package com.anycommunity.usecase.auth.usecase.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.domain.user.service.UserAuthService
import com.anycommunity.usecase.auth.port.command.model.SignupCommand

@Service
class SignupUsecase(
    private val authService: UserAuthService,
) {
    @Transactional
    operator fun invoke(command: SignupCommand) {
        authService.signUp(userCredentials = command.toUserCredentials(), gender = command.gender)
    }
}
