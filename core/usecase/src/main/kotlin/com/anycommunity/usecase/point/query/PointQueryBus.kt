package com.anycommunity.usecase.point.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.point.query.usecase.GetPointRankingQuery
import com.anycommunity.usecase.point.query.usecase.GetPointRankingResult
import com.anycommunity.usecase.point.query.usecase.GetPointRankingUsecase

sealed interface PointQueryBus {
    infix fun ask(query: GetPointRankingQuery): List<GetPointRankingResult>
}

@Service
internal class PointQueryBusImpl(
    private val getPointRankingUsecase: GetPointRankingUsecase,
) : PointQueryBus {
    override fun ask(query: GetPointRankingQuery) = getPointRankingUsecase(query)
}
