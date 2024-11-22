package com.anycommunity.mvcapi.user.response

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.definition.user.Username
import com.anycommunity.usecase.follow.query.usecase.GetFollowerResult
import com.anycommunity.usecase.follow.query.usecase.GetFollowingResult

@Schema(description = "유저 팔로우 응답")
data class UserFollowResponse(
    @field:Schema(description = "유저 id", example = "1")
    val id: Long,
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
) {
    companion object {
        fun from(result: GetFollowerResult) = UserFollowResponse(
            id = result.id,
            username = result.username,
        )

        fun from(result: GetFollowingResult) = UserFollowResponse(
            id = result.id,
            username = result.username,
        )
    }
}
