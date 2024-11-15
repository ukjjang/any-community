package com.jinuk.toy.applicaiton.point.command.usecase

import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.point.service.PointTransactionCommandService
import com.jinuk.toy.domain.user.service.UserCommandService
import com.jinuk.toy.infra.redis.lock.distributedLock

@Service
class PointProcessUsecase(
    private val userCommandService: UserCommandService,
    private val pointTransactionCommandService: PointTransactionCommandService,
) {
    operator fun invoke(command: PointProcessCommand, skipLock: Boolean = false) = with(command) {
        distributedLock(
            key = "PointProcessUsecase:$userId",
            transactional = true,
            skipLock = skipLock,
        ) {
            pointTransactionCommandService.save(userId = userId, point = point, description = description)
            userCommandService.updateTotalPoints(useId = userId, point = point)
            return@distributedLock
        }
    }
}

data class PointProcessCommand(
    val userId: Long,
    val point: Point,
    val description: String,
)
