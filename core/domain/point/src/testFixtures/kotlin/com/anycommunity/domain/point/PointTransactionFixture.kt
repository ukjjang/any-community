package com.anycommunity.domain.point

import org.springframework.stereotype.Component
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.jpa.PointTransactionRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

@Component
class PointTransactionFixture(
    private val pointTransactionRepository: PointTransactionRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            amount: Point = Point(faker.randomLong()),
            description: String = faker.randomString(),
        ) = PointTransaction(
            userId = userId,
            amount = amount,
            description = description,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        amount: Point = Point(faker.randomLong()),
        description: String = faker.randomString(),
    ) = pointTransactionRepository.save(
        create(
            userId = userId,
            amount = amount,
            description = description,
        ),
    )
}
