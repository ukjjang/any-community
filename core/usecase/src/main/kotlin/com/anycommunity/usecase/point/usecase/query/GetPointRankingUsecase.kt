package com.anycommunity.usecase.point.usecase.query

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
import com.anycommunity.usecase.point.port.query.model.GetPointRankingQuery
import com.anycommunity.usecase.point.port.query.model.GetPointRankingResult
import com.anycommunity.util.custompage.toCustomPage

@Service
class GetPointRankingUsecase(
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetPointRankingQuery) = cached(
        key = "GetPointRankingUsecase:${query.hashCode()}",
        expire = Duration.ofMinutes(10),
    ) {
        val pages = userQueryService.getPointRankingUser(query.pageable())
        val content = pages.mapIndexed { idx, user ->
            val rank = query.pageable().offset + idx + 1
            GetPointRankingResult.from(user, rank)
        }

        pages.toCustomPage(content)
    }
}
