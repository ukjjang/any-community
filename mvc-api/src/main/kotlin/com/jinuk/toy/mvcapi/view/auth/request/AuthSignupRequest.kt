package com.jinuk.toy.mvcapi.view.auth.request

import com.jinuk.toy.applicaiton.auth.command.usecase.SignupCommand
import com.jinuk.toy.constant.user.Gender
import com.jinuk.toy.domain.user.value.Username

data class AuthSignupRequest(
    val username: Username,
    val password: String,
    val gender: Gender,
)

internal fun AuthSignupRequest.toCommand() = SignupCommand(username, password, gender)
