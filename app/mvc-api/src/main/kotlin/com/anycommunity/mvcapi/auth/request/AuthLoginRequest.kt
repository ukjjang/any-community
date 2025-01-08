package com.anycommunity.mvcapi.auth.request

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.usecase.auth.port.query.model.LoginQuery

@Schema(description = "로그인 요청")
data class AuthLoginRequest(
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "비밀번호", example = "Password1234")
    val password: RawPassword,
) {
    fun toQuery() = LoginQuery(username, password)
}
