package com.anycommunity.mvcapi.auth.request

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.definition.user.Gender
import com.anycommunity.definition.user.RawPassword
import com.anycommunity.definition.user.Username
import com.anycommunity.usecase.auth.command.usecase.SignupCommand

@Schema(description = "회원가입 요청")
data class AuthSignupRequest(
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "비밀번호", example = "Password1234")
    val password: RawPassword,
    @field:Schema(description = "성별", example = "MALE")
    val gender: Gender,
) {
    fun toCommand() = SignupCommand(username, password, gender)
}
