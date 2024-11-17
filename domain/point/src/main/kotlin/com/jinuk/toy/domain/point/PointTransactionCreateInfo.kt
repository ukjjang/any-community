package com.jinuk.toy.domain.point

import com.jinuk.toy.common.value.point.Point

data class PointTransactionCreateInfo(
    val userId: Long,
    val amount: Point,
    val description: String,
)
