package com.anycommunity.domain.point.jpa

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import com.anycommunity.domain.point.PointTransaction
import com.anycommunity.infra.mysql.point.entity.PointTransactionEntity
import com.anycommunity.infra.mysql.point.jpa.PointEntityTransactionEntityRepository

@Repository
class PointTransactionRepository(
    private val pointTransactionEntityRepository: PointEntityTransactionEntityRepository,
) {
    fun save(pointTransaction: PointTransaction) =
        pointTransactionEntityRepository.save(pointTransaction.toEntity()).toModel()

    fun delete(pointTransaction: PointTransaction) =
        pointTransactionEntityRepository.delete(pointTransaction.toEntity())

    fun findByUserIdOrderByIdDesc(userId: Long, pageable: Pageable) =
        pointTransactionEntityRepository.findByUserIdOrderByIdDesc(userId, pageable).map { it.toModel() }
}

private fun PointTransactionEntity.toModel() = PointTransaction(
    _id = id,
    userId = userId,
    amount = amount,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

private fun PointTransaction.toEntity() = PointTransactionEntity(
    id = _id,
    userId = userId,
    amount = amount,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
