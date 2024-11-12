package com.jinuk.toy.infra.rdb.point.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.jinuk.toy.infra.rdb.point.entity.PointRuleEntity

interface PointRuleEntityRepository : JpaRepository<PointRuleEntity, Long>
