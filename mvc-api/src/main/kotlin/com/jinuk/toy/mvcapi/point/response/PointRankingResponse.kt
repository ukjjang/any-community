package com.jinuk.toy.mvcapi.point.response

import com.jinuk.toy.applicaiton.point.query.usecase.GetPointRankingResult
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.common.value.user.Username

data class PointRankingResponse(
    val ranking: Int,
    val userId: Long,
    val username: Username,
    val totalPoints: Point,
) {
    companion object {
        fun from(result: GetPointRankingResult) = with(result) {
            PointRankingResponse(
                ranking = ranking,
                userId = userId,
                username = username,
                totalPoints = totalPoints,
            )
        }
    }
}
