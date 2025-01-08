package com.anycommunity.usecase.point.port.command.model

import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.PointTransactionCreateInfo

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
