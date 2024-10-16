package com.jinuk.toy.externalapi.view.auth.request

import com.jinuk.toy.applicaiton.auth.command.usecase.SignupCommand
import com.jinuk.toy.applicaiton.auth.query.usecase.LoginQuery
import com.jinuk.toy.domain.user.value.Username

data class AuthCredentialsRequest(val username: Username, val password: String)

internal fun AuthCredentialsRequest.toQuery() = LoginQuery(username, password)
internal fun AuthCredentialsRequest.toCommand() = SignupCommand(username, password)
