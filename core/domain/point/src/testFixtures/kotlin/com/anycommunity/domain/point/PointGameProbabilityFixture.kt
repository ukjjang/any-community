package com.anycommunity.domain.point

import org.springframework.stereotype.Component
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.jpa.PointGameProbabilityRepository
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

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
