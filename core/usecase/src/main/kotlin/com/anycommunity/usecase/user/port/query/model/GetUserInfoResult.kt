package com.anycommunity.usecase.user.port.query.model

import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User

data class GetUserInfoResult(
    val id: Long,
    val username: Username,
    val totalPoints: Point,
) {
    companion object {
        fun from(user: User) = with(user) {
            GetUserInfoResult(
                id = id,
                username = username,
                totalPoints = totalPoints,
            )
        }
    }
}
