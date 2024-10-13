package com.jinuk.toy.externalapi.view.auth

import com.jinuk.toy.domain.user.service.UserAuthService
import com.jinuk.toy.externalapi.view.auth.request.AuthCredentialsRequest
import com.jinuk.toy.externalapi.view.auth.request.toUserCredentials
import com.jinuk.toy.externalapi.view.auth.response.AuthLoginResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "인증")
@RequestMapping("/auth")
@RestController
class AuthAPI(
    private val authService: UserAuthService,
) {

    @PostMapping("/login")
    fun login(@RequestBody authCredentialsRequest: AuthCredentialsRequest) =
        AuthLoginResponse(authService.login(authCredentialsRequest.toUserCredentials()))

    @PostMapping("/signup")
    fun signup(@RequestBody authCredentialsRequest: AuthCredentialsRequest) {
        authService.signUp(authCredentialsRequest.toUserCredentials())
    }
}
