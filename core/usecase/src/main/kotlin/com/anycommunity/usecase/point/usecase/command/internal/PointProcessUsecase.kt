package com.anycommunity.usecase.point.usecase.command.internal

import org.springframework.stereotype.Service
import com.anycommunity.domain.point.service.PointTransactionCommandService
import com.anycommunity.domain.user.service.UserCommandService
import com.anycommunity.infra.redis.lock.distributedLock
import com.anycommunity.usecase.point.port.command.model.PointProcessCommand

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
