package com.anycommunity.usecase.auth.port.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.auth.port.command.model.SignupCommand
import com.anycommunity.usecase.auth.usecase.command.SignupUsecase

sealed interface AuthCommandPort {
    infix fun execute(command: SignupCommand)
}

@Service
internal class AuthCommandPortImpl(
    private val signupUsecase: SignupUsecase,
) : AuthCommandPort {
    override fun execute(command: SignupCommand) = signupUsecase(command)
}
