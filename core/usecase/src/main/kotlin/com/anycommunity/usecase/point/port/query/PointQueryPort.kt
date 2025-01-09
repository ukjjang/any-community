package com.anycommunity.usecase.point.port.query

import org.springframework.stereotype.Service
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryQuery
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryResult
import com.anycommunity.usecase.point.port.query.model.GetPointRankingQuery
import com.anycommunity.usecase.point.port.query.model.GetPointRankingResult
import com.anycommunity.usecase.point.usecase.query.GetPointHistoryUsecase
import com.anycommunity.usecase.point.usecase.query.GetPointRankingUsecase
import com.anycommunity.util.custompage.CustomPage

sealed interface PointQueryPort {
    infix fun ask(query: GetPointRankingQuery): CustomPage<GetPointRankingResult>
    infix fun ask(query: GetPointHistoryQuery): CustomPage<GetPointHistoryResult>
}

@Service
internal class PointQueryPortImpl(
    private val getPointRankingUsecase: GetPointRankingUsecase,
    private val getPointHistoryUsecase: GetPointHistoryUsecase,
) : PointQueryPort {
    override fun ask(query: GetPointRankingQuery) = getPointRankingUsecase(query)
    override fun ask(query: GetPointHistoryQuery) = getPointHistoryUsecase(query)
}
