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
import com.anycommunity.mvcapi.point.response.PointHistoryResponse
import com.anycommunity.mvcapi.point.response.PointRankingResponse
import com.anycommunity.usecase.point.port.command.PointCommandPort
import com.anycommunity.usecase.point.port.command.model.PointGameCommand
import com.anycommunity.usecase.point.port.query.PointQueryPort
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryQuery
import com.anycommunity.usecase.point.port.query.model.GetPointRankingQuery
import com.anycommunity.util.custompage.toCustomPage

@Tag(name = "포인트")
@MvcApiController
class PointApi(
    private val pointQueryPort: PointQueryPort,
    private val pointCommandPort: PointCommandPort,
) {
    @Operation(summary = "포인트 랭킹 유저 조회")
    @GetMapping("/v1/point/ranking")
    fun getPointRanking(@RequestParam page: Int = 1, @RequestParam size: Int = 20) =
        pointQueryPort.ask(GetPointRankingQuery(page, size)).map(PointRankingResponse::from).toCustomPage()

    @Operation(summary = "포인트 게임")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/point/game")
    fun pointGame(@AuthenticationPrincipal user: AuthUser) = pointCommandPort execute PointGameCommand(user.id)

    @Operation(summary = "포인트 히스토리 조회")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/point/history")
    fun getPointHistory(
        @AuthenticationPrincipal user: AuthUser,
        @RequestParam page: Int = 1,
        @RequestParam size: Int = 20,
    ) = pointQueryPort.ask(GetPointHistoryQuery(user.id, page, size)).map(PointHistoryResponse::from).toCustomPage()
}
