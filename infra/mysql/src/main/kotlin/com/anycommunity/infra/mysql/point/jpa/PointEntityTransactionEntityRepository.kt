package com.anycommunity.infra.mysql.point.jpa

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.infra.mysql.point.entity.PointTransactionEntity

interface PointEntityTransactionEntityRepository : JpaRepository<PointTransactionEntity, Long> {

    fun findByUserIdOrderByIdDesc(userId: Long, pageable: Pageable): Page<PointTransactionEntity>
}
