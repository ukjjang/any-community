package com.jinuk.toy.infra.rdb.point.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.point.entity.PointTransactionEntity

interface PointTransactionEntityRepository : JpaRepository<PointTransactionEntity, Long>
