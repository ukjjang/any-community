package com.jinuk.toy.mvcapi.view.auth.response

import com.jinuk.toy.applicaiton.auth.query.result.LoginResult

data class AuthLoginResponse(
    val token: String,
) {
    companion object {
        fun from(result: LoginResult) = AuthLoginResponse(result.token)
    }
}
