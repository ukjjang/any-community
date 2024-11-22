package com.anycommunity.mvcapi.point

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import com.anycommunity.mvcapi.global.MvcApiController
import com.anycommunity.mvcapi.global.security.AuthRole
import com.anycommunity.mvcapi.global.security.AuthUser
import com.anycommunity.mvcapi.point.response.PointRankingResponse
import com.anycommunity.usecase.point.command.PointCommandBus
import com.anycommunity.usecase.point.command.usecase.PointGameCommand
import com.anycommunity.usecase.point.query.PointQueryBus
import com.anycommunity.usecase.point.query.usecase.GetPointRankingQuery

@Tag(name = "포인트")
@MvcApiController
class PointApi(
    private val pointQueryBus: PointQueryBus,
    private val pointCommandBus: PointCommandBus,
) {
    @Operation(summary = "포인트 랭킹 유저 조회")
    @GetMapping("/v1/point/ranking")
    fun getPointRanking(@RequestParam limit: Int = 10) =
        pointQueryBus.ask(GetPointRankingQuery(limit)).map(PointRankingResponse::from)

    @Operation(summary = "포인트 게임")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/point/game")
    fun pointGame(@AuthenticationPrincipal user: AuthUser) = pointCommandBus execute PointGameCommand(user.id)
}
