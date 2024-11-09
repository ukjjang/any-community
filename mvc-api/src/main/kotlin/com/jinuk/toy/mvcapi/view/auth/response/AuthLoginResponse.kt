package com.jinuk.toy.mvcapi.view.auth.response

import io.swagger.v3.oas.annotations.media.Schema
import com.jinuk.toy.applicaiton.auth.query.usecase.LoginResult

@Schema(description = "로그인 응답")
data class AuthLoginResponse(
    @field:Schema(description = "Auth Bearer 토큰")
    val token: String,
) {
    companion object {
        fun from(result: LoginResult) = AuthLoginResponse(result.token)
    }
}
