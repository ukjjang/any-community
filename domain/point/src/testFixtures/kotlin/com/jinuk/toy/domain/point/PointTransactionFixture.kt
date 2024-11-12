package com.jinuk.toy.domain.point

import org.springframework.stereotype.Component
import com.jinuk.toy.domain.point.jpa.PointTransactionRepository
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString

@Component
class PointTransactionFixture(
    private val pointTransactionRepository: PointTransactionRepository,
) {
    companion object {
        fun create(
            userId: Long = faker.randomLong(),
            amount: Long = faker.randomLong(),
            description: String = faker.randomString(),
        ) = PointTransaction(
            userId = userId,
            amount = amount,
            description = description,
        )
    }

    fun persist(
        userId: Long = faker.randomLong(),
        amount: Long = faker.randomLong(),
        description: String = faker.randomString(),
    ) = pointTransactionRepository.save(
        create(
            userId = userId,
            amount = amount,
            description = description,
        ),
    )
}
