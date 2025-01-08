package com.anycommunity.usecase.point.port.command.model

data class PointGameCommand(
    val userId: Long,
) {
    val lockKey: String
        get() = "userId:$userId"
}
