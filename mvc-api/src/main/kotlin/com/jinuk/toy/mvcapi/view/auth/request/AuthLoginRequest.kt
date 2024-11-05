package com.jinuk.toy.mvcapi.view.auth.request

import com.jinuk.toy.applicaiton.auth.query.usecase.LoginQuery
import com.jinuk.toy.domain.user.value.Username

data class AuthLoginRequest(
    val username: Username,
    val password: String,
)

internal fun AuthLoginRequest.toQuery() = LoginQuery(username, password)
