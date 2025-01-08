package com.anycommunity.usecase.auth.port.command.model

import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.UserCredentials

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
