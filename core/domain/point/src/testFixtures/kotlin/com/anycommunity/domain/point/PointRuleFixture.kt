package com.anycommunity.domain.point

import org.springframework.stereotype.Component
import com.anycommunity.definition.point.Point
import com.anycommunity.definition.point.PointRuleType
import com.anycommunity.domain.point.jpa.PointRuleRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

@Component
class PointRuleFixture(
    private val pointRuleRepository: PointRuleRepository,
) {
    companion object {
        fun create(
            ruleType: PointRuleType = faker.randomEnum<PointRuleType>(),
            amount: Point = Point(faker.randomLong()),
            description: String = faker.randomString(),
        ) = PointRule(
            ruleType = ruleType,
            amount = amount,
            description = description,
        )
    }

    fun persist(
        id: Long? = null,
        ruleType: PointRuleType = faker.randomEnum<PointRuleType>(),
        amount: Point = Point(faker.randomLong()),
        description: String = faker.randomString(),
    ) = pointRuleRepository.save(
        create(
            ruleType = ruleType,
            amount = amount,
            description = description,
        ),
    )
}
