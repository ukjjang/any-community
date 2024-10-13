package com.jinuk.toy.externalapi.view.auth.request

import com.jinuk.toy.domain.user.UserCredentials
import com.jinuk.toy.domain.user.value.Username

data class AuthCredentialsRequest(val username: Username, val password: String)

internal fun AuthCredentialsRequest.toUserCredentials() = UserCredentials(username, password)
