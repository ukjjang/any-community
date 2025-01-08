package com.anycommunity.mvcapi.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.anycommunity.mvcapi.auth.request.AuthLoginRequest
import com.anycommunity.mvcapi.auth.request.AuthSignupRequest
import com.anycommunity.mvcapi.auth.response.AuthLoginResponse
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.usecase.auth.port.command.AuthCommandPort
import com.anycommunity.usecase.auth.port.query.AuthQueryPort

@Tag(name = "인증")
@MvcApiController
class AuthApi(
    private val authCommandPort: AuthCommandPort,
    private val authQueryPort: AuthQueryPort,
) {
    @Operation(summary = "로그인")
    @PostMapping("/v1/auth/login")
    fun login(@RequestBody request: AuthLoginRequest) = AuthLoginResponse.from(authQueryPort ask request.toQuery())

    @Operation(summary = "회원가입")
    @PostMapping("/v1/auth/signup")
    fun signup(@RequestBody request: AuthSignupRequest) = authCommandPort execute request.toCommand()
}
