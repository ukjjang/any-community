package com.anycommunity.usecase.point.port.query.model

import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User

data class GetPointRankingResult(
    val ranking: Long,
    val userId: Long,
    val username: Username,
    val totalPoints: Point,
) {
    companion object {
        fun from(user: User, ranking: Long) = GetPointRankingResult(
            ranking = ranking,
            userId = user.id,
            username = user.username,
            totalPoints = user.totalPoints,
        )
    }
}
