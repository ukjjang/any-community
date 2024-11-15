package com.jinuk.toy.mvcapi.point

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import com.jinuk.toy.applicaiton.point.query.PointQueryBus
import com.jinuk.toy.applicaiton.point.query.usecase.GetPointRankingQuery
import com.jinuk.toy.mvcapi.global.MvcAPIController
import com.jinuk.toy.mvcapi.point.response.PointRankingResponse

@Tag(name = "포인트")
@MvcAPIController
class PointApi(
    private val pointQueryBus: PointQueryBus,
) {
    @Operation(summary = "유저 정보")
    @GetMapping("/v1/point/ranking")
    fun getPointRanking(@RequestParam limit: Int = 10) = (
        pointQueryBus ask GetPointRankingQuery(limit)
        ).map { PointRankingResponse.from(it) }
}
