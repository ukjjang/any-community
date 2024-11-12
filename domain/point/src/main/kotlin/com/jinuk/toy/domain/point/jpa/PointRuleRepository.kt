package com.jinuk.toy.domain.point.jpa

import org.springframework.stereotype.Repository
import com.jinuk.toy.domain.point.PointRule
import com.jinuk.toy.domain.point.toEntity
import com.jinuk.toy.domain.point.toModel
import com.jinuk.toy.infra.rdb.point.jpa.PointRuleEntityRepository

@Repository
class PointRuleRepository(
    private val pointRuleEntityRepository: PointRuleEntityRepository,
) {
    fun save(pointRule: PointRule) = pointRuleEntityRepository.save(pointRule.toEntity()).toModel()

    fun delete(pointRule: PointRule) = pointRuleEntityRepository.delete(pointRule.toEntity())
}
