package com.jinuk.toy.mvcapi.auth.request

import io.swagger.v3.oas.annotations.media.Schema
import com.jinuk.toy.applicaiton.auth.query.usecase.LoginQuery
import com.jinuk.toy.common.define.user.Username

@Schema(description = "로그인 요청")
data class AuthLoginRequest(
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "비밀번호", example = "password1234")
    val password: String,
)

internal fun AuthLoginRequest.toQuery() = LoginQuery(username, password)
