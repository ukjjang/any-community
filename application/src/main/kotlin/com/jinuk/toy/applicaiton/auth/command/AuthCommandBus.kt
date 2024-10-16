package com.jinuk.toy.applicaiton.auth.command

import com.jinuk.toy.applicaiton.auth.command.usecase.SignupCommand
import com.jinuk.toy.applicaiton.auth.command.usecase.SignupUsecase
import org.springframework.stereotype.Service

sealed interface AuthCommandBus {
    infix fun execute(command: SignupCommand)
}

@Service
internal class AuthCommandBusImpl(
    private val signupUsecase: SignupUsecase,
) : AuthCommandBus {
    override fun execute(command: SignupCommand) = signupUsecase(command)
}
