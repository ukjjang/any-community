package com.anycommunity.usecase.point.port.query.model

import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.PointTransaction

data class GetPointHistoryResult(
    val amount: Point,
    val description: String,
) {
    companion object {
        fun from(pointTransaction: PointTransaction) = GetPointHistoryResult(
            amount = pointTransaction.amount,
            description = pointTransaction.description,
        )
    }
}
