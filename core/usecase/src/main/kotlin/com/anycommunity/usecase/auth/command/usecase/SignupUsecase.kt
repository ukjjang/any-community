package com.anycommunity.usecase.auth.command.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.UserCredentials
import com.anycommunity.domain.user.service.UserAuthService

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
    val password: RawPassword,
    val gender: Gender,
) {
    fun toUserCredentials() = UserCredentials(
        username = username,
        password = password,
    )
}
