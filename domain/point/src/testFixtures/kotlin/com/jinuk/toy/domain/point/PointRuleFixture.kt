package com.jinuk.toy.domain.point

import org.springframework.stereotype.Component
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.domain.point.jpa.PointRuleRepository

@Component
class PointRuleFixture(
    private val pointRuleRepository: PointRuleRepository,
) {
    companion object {
        fun create(
            ruleType: String = faker.randomString(),
            amount: Long = faker.randomLong(),
            description: String = faker.randomString(),
        ) = PointRule(
            ruleType = ruleType,
            amount = amount,
            description = description,
        )
    }

    fun persist(
        id: Long? = null,
        ruleType: String = faker.randomString(),
        amount: Long = faker.randomLong(),
        description: String = faker.randomString(),
    ) = pointRuleRepository.save(
        create(
            ruleType = ruleType,
            amount = amount,
            description = description,
        ),
    )
}
