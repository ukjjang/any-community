package com.jinuk.toy.mvcapi.auth.request

import io.swagger.v3.oas.annotations.media.Schema
import com.jinuk.toy.applicaiton.auth.command.usecase.SignupCommand
import com.jinuk.toy.common.define.user.Gender
import com.jinuk.toy.common.define.user.Username

@Schema(description = "회원가입 요청")
data class AuthSignupRequest(
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "비밀번호", example = "password1234")
    val password: String,
    @field:Schema(description = "성별", example = "MALE")
    val gender: Gender,
)

internal fun AuthSignupRequest.toCommand() = SignupCommand(username, password, gender)
