package com.jinuk.toy.domain.point

import org.springframework.stereotype.Component
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.point.jpa.PointGameProbabilityRepository

@Component
class PointGameProbabilityFixture(
    private val pointGameProbabilityRepository: PointGameProbabilityRepository,
) {
    companion object {
        fun create(point: Point = Point(faker.randomLong()), probability: Int = faker.random().nextInt()) =
            PointGameProbability(
                point = point,
                probability = probability,
            )
    }

    fun persist(point: Point = Point(faker.randomLong()), probability: Int = faker.random().nextInt()) =
        pointGameProbabilityRepository.save(
            create(
                point = point,
                probability = probability,
            ),
        )
}
