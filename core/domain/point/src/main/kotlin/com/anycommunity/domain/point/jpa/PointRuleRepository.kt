package com.anycommunity.domain.point.jpa

import org.springframework.stereotype.Repository
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.domain.point.PointRule
import com.anycommunity.domain.point.toEntity
import com.anycommunity.domain.point.toModel
import com.anycommunity.infra.mysql.point.jpa.PointRuleEntityRepository

@Repository
class PointRuleRepository(
    private val pointRuleEntityRepository: PointRuleEntityRepository,
) {
    fun save(pointRule: PointRule) = pointRuleEntityRepository.save(pointRule.toEntity()).toModel()

    fun delete(pointRule: PointRule) = pointRuleEntityRepository.delete(pointRule.toEntity())

    fun findByRuleType(ruleType: PointRuleType) = pointRuleEntityRepository.findByRuleType(ruleType)?.toModel()
}
