package com.jinuk.toy.applicaiton.auth.command.usecase

import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService
import com.jinuk.toy.domain.user.value.Username
import org.springframework.stereotype.Service

@Service
class SignupUsecase(
    private val authService: UserAuthService
) {
    operator fun invoke(command: SignupCommand) {
        authService.signUp(command.toUserCredentials())
    }
}

data class SignupCommand(
    val username: Username,
    val password: String,
)

private fun SignupCommand.toUserCredentials() = UserCredentials(
    username = username,
    password = password,
)
