package com.anycommunity.mvcapi.user.response

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.usecase.user.query.usecase.GetUserInfoResult

@Schema(description = "유저 정보 응답")
data class UserInfoResponse(
    @field:Schema(description = "유저 id", example = "1")
    val id: Long,
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "포인트", example = "1000")
    val totalPoints: Point,
) {
    companion object {
        fun from(result: GetUserInfoResult) = with(result) {
            UserInfoResponse(
                id = id,
                username = username,
                totalPoints = totalPoints,
            )
        }
    }
}
