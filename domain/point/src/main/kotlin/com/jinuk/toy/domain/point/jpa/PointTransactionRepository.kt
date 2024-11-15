package com.jinuk.toy.domain.point.jpa

import org.springframework.stereotype.Repository
import com.jinuk.toy.domain.point.PointTransaction
import com.jinuk.toy.domain.point.toEntity
import com.jinuk.toy.domain.point.toModel
import com.jinuk.toy.infra.rdb.point.jpa.PointTransactionEntityRepository

@Repository
class PointTransactionRepository(
    private val pointTransactionEntityRepository: PointTransactionEntityRepository,
) {
    fun save(pointTransaction: PointTransaction) =
        pointTransactionEntityRepository.save(pointTransaction.toEntity()).toModel()

    fun delete(pointTransaction: PointTransaction) =
        pointTransactionEntityRepository.delete(pointTransaction.toEntity())
}
