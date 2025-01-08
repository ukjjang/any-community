package com.anycommunity.mvcapi.user.response

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import com.anycommunity.definition.user.Username
import com.anycommunity.usecase.follow.port.query.model.GetFollowerResult
import com.anycommunity.usecase.follow.port.query.model.GetFollowingResult

@Schema(description = "유저 팔로우 응답")
data class UserFollowResponse(
    @field:Schema(description = "유저 id", example = "1")
    val id: Long,
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "팔로우 시간", example = "2024-01-01T12:00:00")
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(result: GetFollowerResult) = UserFollowResponse(
            id = result.id,
            username = result.username,
            createdAt = result.createdAt,
        )

        fun from(result: GetFollowingResult) = UserFollowResponse(
            id = result.id,
            username = result.username,
            createdAt = result.createdAt,
        )
    }
}
