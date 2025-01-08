package com.anycommunity.usecase.point.command.usecase

import org.springframework.stereotype.Service
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.pick
import com.anycommunity.domain.point.service.PointGameProbabilityQueryService
import com.anycommunity.infra.redis.lock.distributedLock
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessCommand
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase

@Service
class PointGameUsecase(
    private val pointGameProbabilityQueryService: PointGameProbabilityQueryService,
    private val pointProcessUsecase: PointProcessUsecase,
) {
    companion object {
        private const val POINT_GAME_USE_DESCRIPTION_TEMPLATE = "포인트 게임으로 사용"
        private const val POINT_EARN_DESCRIPTION_TEMPLATE = "포인트 게임으로 지급"
        private val POINT_GAME_COST = Point(-10)
    }

    operator fun invoke(command: PointGameCommand) = distributedLock(
        key = "PointProcessUsecase:${command.lockKey}",
        transactional = true,
    ) {
        val useCommand = PointProcessCommand(
            userId = command.userId,
            point = POINT_GAME_COST,
            description = POINT_GAME_USE_DESCRIPTION_TEMPLATE,
        )
        pointProcessUsecase(useCommand)

        val point = pointGameProbabilityQueryService.findAll().pick()
        if (point != Point.ZERO) {
            val earnCommand = PointProcessCommand(
                userId = command.userId,
                point = point,
                description = POINT_EARN_DESCRIPTION_TEMPLATE,
            )

            pointProcessUsecase(earnCommand)
        }
    }
}

data class PointGameCommand(
    val userId: Long,
) {
    val lockKey: String
        get() = "userId:$userId"
}
