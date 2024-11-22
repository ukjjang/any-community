package com.anycommunity.usecase.point.query.usecase

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.user.Username
import com.anycommunity.domain.user.User
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.infra.redis.cache.cached

@Service
class GetPointRankingUsecase(
    private val userQueryService: UserQueryService,
) {
    @Transactional(readOnly = true)
    operator fun invoke(query: GetPointRankingQuery) = cached(
        key = "GetPointRankingUsecase:${query.limit}",
        expire = Duration.ofMinutes(10),
    ) {
        GetPointRankingResult.from(
            userQueryService.getPointRankingUser(query.limit),
        )
    }
}

data class GetPointRankingQuery(
    val limit: Int,
)

data class GetPointRankingResult(
    val ranking: Int,
    val userId: Long,
    val username: Username,
    val totalPoints: Point,
) {
    companion object {
        fun from(users: List<User>): List<GetPointRankingResult> = users.mapIndexed { idx, user -> from(user, idx + 1) }
        fun from(user: User, ranking: Int) = GetPointRankingResult(
            ranking = ranking,
            userId = user.id,
            username = user.username,
            totalPoints = user.totalPoints,
        )
    }
}
