package com.anycommunity.usecase.point.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.point.port.query.model.GetPointRankingQuery
import com.anycommunity.usecase.point.port.query.model.GetPointRankingResult
import com.anycommunity.usecase.point.usecase.query.GetPointRankingUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface PointQueryPort {
    infix fun ask(query: GetPointRankingQuery): CustomPage<GetPointRankingResult>
}

@Service
internal class PointQueryPortImpl(
    private val getPointRankingUsecase: GetPointRankingUsecase,
) : PointQueryPort {
    override fun ask(query: GetPointRankingQuery) = getPointRankingUsecase(query)
}
