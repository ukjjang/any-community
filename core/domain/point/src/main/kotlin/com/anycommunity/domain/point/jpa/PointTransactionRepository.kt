package com.anycommunity.domain.point.jpa

import org.springframework.stereotype.Repository
import com.anycommunity.domain.point.PointTransaction
import com.anycommunity.domain.point.toEntity
import com.anycommunity.domain.point.toModel
import com.anycommunity.infra.mysql.point.jpa.PointEntityTransactionEntityRepository

@Repository
class PointTransactionRepository(
    private val pointTransactionEntityRepository: PointEntityTransactionEntityRepository,
) {
    fun save(pointTransaction: PointTransaction) =
        pointTransactionEntityRepository.save(pointTransaction.toEntity()).toModel()

    fun delete(pointTransaction: PointTransaction) =
        pointTransactionEntityRepository.delete(pointTransaction.toEntity())
}
