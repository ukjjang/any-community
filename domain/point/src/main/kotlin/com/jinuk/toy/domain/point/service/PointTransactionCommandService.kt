package com.jinuk.toy.domain.point.service

import org.springframework.stereotype.Service
import com.jinuk.toy.domain.point.PointTransaction
import com.jinuk.toy.domain.point.PointTransactionCreateInfo
import com.jinuk.toy.domain.point.jpa.PointTransactionRepository

@Service
class PointTransactionCommandService(
    private val pointTransactionRepository: PointTransactionRepository,
) {
    fun save(pointTransaction: PointTransaction) = pointTransactionRepository.save(pointTransaction)

    fun create(info: PointTransactionCreateInfo) = save(PointTransaction.create(info))
}
