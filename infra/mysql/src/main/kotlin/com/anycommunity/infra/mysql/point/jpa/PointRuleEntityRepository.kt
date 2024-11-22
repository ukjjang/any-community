package com.anycommunity.infra.mysql.point.jpa

import org.springframework.data.jpa.repository.JpaRepository
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.infra.mysql.point.entity.PointRuleEntity

interface PointRuleEntityRepository : JpaRepository<PointRuleEntity, Long> {
    fun findByRuleType(ruleType: PointRuleType): PointRuleEntity?
}
