package com.jinuk.toy.applicaiton.point.query.usecase

import org.springframework.stereotype.Service
import java.time.Duration
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.common.value.user.Username
import com.jinuk.toy.domain.user.User
import com.jinuk.toy.domain.user.service.UserQueryService
import com.jinuk.toy.infra.redis.cache.cached

@Service
class GetPointRankingUsecase(
    private val userQueryService: UserQueryService,
) {
    operator fun invoke(query: GetPointRankingQuery) = cached(
        key = "GetPointRankingUsecase:${query.limit}",
        transactional = true,
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