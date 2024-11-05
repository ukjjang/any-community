package com.jinuk.toy.mvcapi.view.auth

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import com.jinuk.toy.applicaiton.auth.command.AuthCommandBus
import com.jinuk.toy.applicaiton.auth.query.AuthQueryBus
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.view.auth.request.AuthLoginRequest
import com.jinuk.toy.mvcapi.view.auth.request.AuthSignupRequest
import com.jinuk.toy.mvcapi.view.auth.request.toCommand
import com.jinuk.toy.mvcapi.view.auth.request.toQuery
import com.jinuk.toy.mvcapi.view.auth.response.AuthLoginResponse

@Tag(name = "인증")
@MvcAPIController
class AuthAPI(
    private val authCommandBus: AuthCommandBus,
    private val authQueryBus: AuthQueryBus,
) {
    @PostMapping("/v1/auth/login")
    fun login(
        @RequestBody request: AuthLoginRequest,
    ) = AuthLoginResponse.from(authQueryBus ask request.toQuery())

    @PostMapping("/v1/auth/signup")
    fun signup(
        @RequestBody request: AuthSignupRequest,
    ) = authCommandBus execute request.toCommand()
}
