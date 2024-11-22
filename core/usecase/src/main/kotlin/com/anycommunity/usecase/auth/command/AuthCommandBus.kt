package com.anycommunity.usecase.auth.command

import org.springframework.stereotype.Service
import com.anycommunity.usecase.auth.command.usecase.SignupCommand
import com.anycommunity.usecase.auth.command.usecase.SignupUsecase

sealed interface AuthCommandBus {
    infix fun execute(command: SignupCommand)
}

@Service
internal class AuthCommandBusImpl(
    private val signupUsecase: SignupUsecase,
) : AuthCommandBus {
    override fun execute(command: SignupCommand) = signupUsecase(command)
}
