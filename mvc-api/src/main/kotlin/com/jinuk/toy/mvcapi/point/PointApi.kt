package com.jinuk.toy.mvcapi.point

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import com.jinuk.toy.applicaiton.point.command.PointCommandBus
import com.jinuk.toy.applicaiton.point.command.usecase.PointGameCommand
import com.jinuk.toy.applicaiton.point.query.PointQueryBus
import com.jinuk.toy.applicaiton.point.query.usecase.GetPointRankingQuery
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.global.security.AuthRole
import com.jinuk.toy.mvcapi.global.security.AuthUser
import com.jinuk.toy.mvcapi.point.response.PointRankingResponse

@Tag(name = "포인트")
@MvcAPIController
class PointApi(
    private val pointCommandBus: PointCommandBus,
    private val pointQueryBus: PointQueryBus,
) {
    @Operation(summary = "포인트 랭킹 유저 조회")
    @GetMapping("/v1/point/ranking")
    fun getPointRanking(@RequestParam limit: Int = 10) = (
        pointQueryBus ask GetPointRankingQuery(limit)
        ).map { PointRankingResponse.from(it) }

    @Operation(summary = "포인트 게임")
    @Secured(AuthRole.USER)
    @PostMapping("/v1/point/game")
    fun pointGame(@AuthenticationPrincipal user: AuthUser) = pointCommandBus execute PointGameCommand(user.id)
}
