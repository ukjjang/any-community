package com.anycommunity.usecase.point.usecase.command

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.point.PointGameProbabilityFixture
import com.anycommunity.domain.point.service.PointGameProbabilityQueryService
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.port.command.model.PointGameCommand
import com.anycommunity.usecase.point.port.command.model.PointProcessCommand
import com.anycommunity.usecase.point.usecase.command.internal.PointProcessUsecase

class PointGameUsecaseTest(
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("포인트 게임 유스케이스") {
            val pointGameProbabilityQueryService: PointGameProbabilityQueryService = mockk(relaxed = true)
            val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
            val pointGameUsecase =
                PointGameUsecase(pointGameProbabilityQueryService, pointProcessUsecase)

            beforeTest {
                clearMocks(pointGameProbabilityQueryService, pointProcessUsecase)
            }

            it("성공") {
                val user = userFixture.persist(totalPoints = Point(10))

                every { pointGameProbabilityQueryService.findAll() } returns listOf(
                    PointGameProbabilityFixture.create(point = Point(100), probability = 10000),
                )

                pointGameUsecase(PointGameCommand(user.id))

                verify(exactly = 2) {
                    pointProcessUsecase(
                        withArg { command ->
                            command.userId shouldBe user.id
                            command.description.shouldNotBeBlank()
                        },
                    )
                }
            }

            it("게임 결과과 꽝이라 포인트 지급 안됨") {
                val user = userFixture.persist(totalPoints = Point(10))

                every { pointGameProbabilityQueryService.findAll() } returns listOf(
                    PointGameProbabilityFixture.create(point = Point.ZERO, probability = 10000),
                )

                pointGameUsecase(PointGameCommand(user.id))

                verify(exactly = 1) {
                    pointProcessUsecase(any<PointProcessCommand>())
                }
            }
        }
    },
)
