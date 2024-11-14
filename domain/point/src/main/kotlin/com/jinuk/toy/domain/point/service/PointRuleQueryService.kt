package com.jinuk.toy.domain.point.service

import org.springframework.stereotype.Service
import java.time.Duration
import com.jinuk.toy.common.value.point.PointRuleType
import com.jinuk.toy.domain.point.jpa.PointRuleRepository
import com.jinuk.toy.infra.redis.cache.cached

@Service
class PointRuleQueryService(
    private val pointRuleRepository: PointRuleRepository,
) {
    fun findByRuleType(ruleType: PointRuleType) = pointRuleRepository.findByRuleType(ruleType)

    fun getByRuleType(ruleType: PointRuleType) =
        cached(
            key = "PointRuleQueryService:getByRuleType:$ruleType",
            expire = Duration.ofHours(1),
        ) {
            findByRuleType(ruleType)
                ?: throw NoSuchElementException("존재하지 않는 포인트 룰 타입입니다.")
        }
}
