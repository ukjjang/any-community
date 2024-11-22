package com.anycommunity.domain.point

import com.anycommunity.definition.point.Point

data class PointTransactionCreateInfo(
    val userId: Long,
    val amount: Point,
    val description: String,
)
