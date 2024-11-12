package com.jinuk.toy.mvcapi.auth

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.jinuk.toy.applicaiton.auth.command.AuthCommandBus
import com.jinuk.toy.applicaiton.auth.query.AuthQueryBus
import com.jinuk.toy.mvcapi.auth.request.AuthLoginRequest
import com.jinuk.toy.mvcapi.auth.request.AuthSignupRequest
import com.jinuk.toy.mvcapi.auth.request.toCommand
import com.jinuk.toy.mvcapi.auth.request.toQuery
import com.jinuk.toy.mvcapi.auth.response.AuthLoginResponse
import com.jinuk.toy.mvcapi.global.MvcAPIController

@Tag(name = "인증")
@MvcAPIController
class AuthAPI(
    private val authCommandBus: AuthCommandBus,
    private val authQueryBus: AuthQueryBus,
) {
    @Operation(summary = "로그인")
    @PostMapping("/v1/auth/login")
    fun login(
        @RequestBody request: AuthLoginRequest,
    ) = AuthLoginResponse.from(authQueryBus ask request.toQuery())

    @Operation(summary = "회원가입")
    @PostMapping("/v1/auth/signup")
    fun signup(
        @RequestBody request: AuthSignupRequest,
    ) = authCommandBus execute request.toCommand()
}
