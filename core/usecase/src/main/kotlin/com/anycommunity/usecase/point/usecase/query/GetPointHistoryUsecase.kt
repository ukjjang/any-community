package com.anycommunity.usecase.point.usecase.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import com.anycommunity.domain.point.service.PointTransactionQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryQuery
import com.anycommunity.usecase.point.port.query.model.GetPointHistoryResult
import com.anycommunity.util.custompage.toCustomPage

@Service
class GetPointHistoryUsecase(
    private val pointTransactionQueryService: PointTransactionQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetPointHistoryQuery) = cached(
        key = "GetPointHistoryUsecase:${query.hashCode()}",
        expire = Duration.ofMinutes(10),
    ) {
        pointTransactionQueryService.findByUserIdOrderByIdDesc(query.userId, query.pageable())
            .map { GetPointHistoryResult.from(it) }
            .toCustomPage()
    }
}
