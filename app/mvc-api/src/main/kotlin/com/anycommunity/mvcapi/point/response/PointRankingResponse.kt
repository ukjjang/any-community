package com.anycommunity.mvcapi.point.response

import io.swagger.v3.oas.annotations.media.Schema
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.usecase.point.query.usecase.GetPointRankingResult

@Schema(description = "포인트 랭킹 유저 조회 응답")
data class PointRankingResponse(
    @field:Schema(description = "랭킹", example = "1")
    val ranking: Int,
    @field:Schema(description = "유저 id", example = "1")
    val userId: Long,
    @field:Schema(description = "사용자 이름", example = "ukjjang")
    val username: Username,
    @field:Schema(description = "포인트", example = "6100")
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
