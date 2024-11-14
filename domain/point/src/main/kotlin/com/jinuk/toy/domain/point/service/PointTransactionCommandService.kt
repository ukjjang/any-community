package com.jinuk.toy.domain.point.service

import org.springframework.stereotype.Service
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.point.PointTransaction
import com.jinuk.toy.domain.point.jpa.PointTransactionRepository

@Service
class PointTransactionCommandService(
    private val pointTransactionRepository: PointTransactionRepository,
) {
    fun save(
        userId: Long,
        point: Point,
        description: String,
    ) {
        val pointTransaction =
            PointTransaction.of(
                userId = userId,
                amount = point,
                description = description,
            )
        pointTransactionRepository.save(pointTransaction)
    }
}
