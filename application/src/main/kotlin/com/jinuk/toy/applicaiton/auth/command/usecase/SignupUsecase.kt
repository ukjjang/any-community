package com.jinuk.toy.applicaiton.auth.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.jinuk.toy.common.define.user.Gender
import com.jinuk.toy.common.define.user.Username
import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.service.UserAuthService

@Service
class SignupUsecase(
    private val authService: UserAuthService,
) {
    @Transactional
    operator fun invoke(command: SignupCommand) {
        authService.signUp(userCredentials = command.toUserCredentials(), gender = command.gender)
    }
}

data class SignupCommand(
    val username: Username,
    val password: String,
    val gender: Gender,
)

private fun SignupCommand.toUserCredentials() =
    UserCredentials(
        username = username,
        password = password,
    )
