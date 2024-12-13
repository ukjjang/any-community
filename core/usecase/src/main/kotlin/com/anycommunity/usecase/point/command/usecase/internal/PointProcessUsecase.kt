package com.anycommunity.usecase.point.command.usecase.internal

import org.springframework.stereotype.Service
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.PointTransactionCreateInfo
import com.anycommunity.domain.point.service.PointTransactionCommandService
import com.anycommunity.domain.user.service.UserCommandService
import com.anycommunity.infra.redis.lock.distributedLock

@Service
class PointProcessUsecase(
    private val userCommandService: UserCommandService,
    private val pointTransactionCommandService: PointTransactionCommandService,
) {
    operator fun invoke(command: PointProcessCommand) = with(command) {
        distributedLock(
            key = "PointProcessUsecase:$lockKey",
            transactional = true,
        ) {
            pointTransactionCommandService.create(command.toInfo())
            userCommandService.updateTotalPoints(useId = userId, point = point)
            return@distributedLock
        }
    }
}

data class PointProcessCommand(
    val userId: Long,
    val point: Point,
    val description: String,
) {
    val lockKey: String
        get() = "userId:$userId"

    fun toInfo() = PointTransactionCreateInfo(
        userId = userId,
        amount = point,
        description = description,
    )
}
