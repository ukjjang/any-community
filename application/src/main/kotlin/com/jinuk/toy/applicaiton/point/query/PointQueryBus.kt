package com.jinuk.toy.applicaiton.point.query

import org.springframework.stereotype.Service
import com.jinuk.toy.applicaiton.point.query.usecase.GetPointRankingQuery
import com.jinuk.toy.applicaiton.point.query.usecase.GetPointRankingResult
import com.jinuk.toy.applicaiton.point.query.usecase.GetPointRankingUsecase

sealed interface PointQueryBus {
    infix fun ask(query: GetPointRankingQuery): List<GetPointRankingResult>
}

@Service
internal class PointQueryBusImpl(
    private val getPointRankingUsecase: GetPointRankingUsecase,
) : PointQueryBus {
    override fun ask(query: GetPointRankingQuery) = getPointRankingUsecase(query)
}
