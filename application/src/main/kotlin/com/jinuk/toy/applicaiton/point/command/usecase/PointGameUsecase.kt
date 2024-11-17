package com.jinuk.toy.applicaiton.point.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.point.pick
import com.jinuk.toy.domain.point.service.PointGameProbabilityQueryService
import com.jinuk.toy.domain.user.service.UserCommandService
import com.jinuk.toy.infra.redis.lock.distributedLock

@Service
class PointGameUsecase(
    private val pointGameProbabilityQueryService: PointGameProbabilityQueryService,
    private val pointProcessUsecase: PointProcessUsecase,
    private val userCommandService: UserCommandService,
) {
    companion object {
        private const val POINT_DESCRIPTION_TEMPLATE = "포인트 게임으로 지급"
        private val POINT_GAME_COST = Point(-10)
    }

    operator fun invoke(command: PointGameCommand) = distributedLock(
        key = "PointProcessUsecase:${command.lockKey}",
        transactional = true,
    ) {
        userCommandService.updateTotalPoints(command.userId, POINT_GAME_COST)

        val point = pointGameProbabilityQueryService.findAll().pick()
        if (point != Point.ZERO) {
            val processCommand = PointProcessCommand(
                userId = command.userId,
                point = point,
                description = POINT_DESCRIPTION_TEMPLATE,
            )
            pointProcessUsecase(command = processCommand, skipLock = true)
        }
    }
}

data class PointGameCommand(
    val userId: Long,
) {
    val lockKey: String
        get() = "userId:$userId"
}
