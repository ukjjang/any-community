package com.anycommunity.domain.point

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeTrue

class PointGameProbabilityTest : DescribeSpec(
    {
        describe("PointGameProbability 테스트") {
            context("유효한 확률 리스트가 주어졌을 때") {
                val probabilities = listOf(
                    PointGameProbabilityFixture.create(probability = 1000),
                    PointGameProbabilityFixture.create(probability = 1000),
                    PointGameProbabilityFixture.create(probability = 2000),
                    PointGameProbabilityFixture.create(probability = 3000),
                    PointGameProbabilityFixture.create(probability = 3000),
                )

                it("총 확률이 10000일 경우 포인트를 반환한다") {
                    repeat(10) {
                        val point = probabilities.pick()
                        probabilities.map { it.point }.contains(point).shouldBeTrue()
                    }
                }
            }

            context("확률 합계가 10000이 아닐 때") {
                val invalidProbabilities = listOf(
                    PointGameProbabilityFixture.create(probability = 1000),
                    PointGameProbabilityFixture.create(probability = 3000),
                    PointGameProbabilityFixture.create(probability = 5000),
                )

                it("예외를 던진다") {
                    shouldThrow<IllegalArgumentException> {
                        invalidProbabilities.pick()
                    }
                }
            }

            context("빈 리스트가 주어졌을 때") {
                val emptyProbabilities = emptyList<PointGameProbability>()

                it("예외를 던진다") {
                    shouldThrow<IllegalArgumentException> {
                        emptyProbabilities.pick()
                    }
                }
            }
        }
    },
)
