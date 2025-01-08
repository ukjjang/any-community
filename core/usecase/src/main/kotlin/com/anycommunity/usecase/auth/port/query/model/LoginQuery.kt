package com.anycommunity.usecase.auth.port.query.model

import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.UserCredentials

data class LoginQuery(
    val username: Username,
    val password: RawPassword,
) {
    fun toUserCredentials() = UserCredentials(
        username = username,
        password = password,
    )
}
