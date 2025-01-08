package com.anycommunity.usecase.point.query.usecase

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached
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

data class GetPointRankingQuery(
    val page: Int,
    val size: Int,
) {
    fun pageable(): Pageable = PageRequest.of(page - 1, size)
}

data class GetPointRankingResult(
    val ranking: Long,
    val userId: Long,
    val username: Username,
    val totalPoints: Point,
) {
    companion object {
        fun from(user: User, ranking: Long) = GetPointRankingResult(
            ranking = ranking,
            userId = user.id,
            username = user.username,
            totalPoints = user.totalPoints,
        )
    }
}
