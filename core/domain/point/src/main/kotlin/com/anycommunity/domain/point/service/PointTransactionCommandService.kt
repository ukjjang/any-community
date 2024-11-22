package com.anycommunity.domain.point.service

import org.springframework.stereotype.Service
import com.anycommunity.domain.point.PointTransaction
import com.anycommunity.domain.point.PointTransactionCreateInfo
import com.anycommunity.domain.point.jpa.PointTransactionRepository

@Service
class PointTransactionCommandService(
    private val pointTransactionRepository: PointTransactionRepository,
) {
    fun save(pointTransaction: PointTransaction) = pointTransactionRepository.save(pointTransaction)

    fun create(info: PointTransactionCreateInfo) = save(PointTransaction.create(info))
}
