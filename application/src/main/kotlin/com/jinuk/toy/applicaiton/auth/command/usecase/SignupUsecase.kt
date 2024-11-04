package com.jinuk.toy.applicaiton.auth.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService
import com.jinuk.toy.domain.user.value.Username

@Service
class SignupUsecase(
    private val authService: UserAuthService,
) {
    @Transactional
    operator fun invoke(command: SignupCommand) {
        authService.signUp(command.toUserCredentials())
    }
}

data class SignupCommand(
    val username: Username,
    val password: String,
)

private fun SignupCommand.toUserCredentials() =
    UserCredentials(
        username = username,
        password = password,
    )
