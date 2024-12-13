package com.anycommunity.usecase.point.command

import io.kotest.assertions.throwables.shouldThrow
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
import com.anycommunity.domain.user.service.UserCommandService
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.command.usecase.PointGameCommand
import com.anycommunity.usecase.point.command.usecase.PointGameUsecase
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessCommand
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase

class PointGameUsecaseTest(
    private val userCommandService: UserCommandService,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("포인트 게임 유스케이스") {
            val pointGameProbabilityQueryService: PointGameProbabilityQueryService = mockk(relaxed = true)
            val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
            val pointGameUsecase =
                PointGameUsecase(pointGameProbabilityQueryService, pointProcessUsecase, userCommandService)

            beforeTest {
                clearMocks(pointGameProbabilityQueryService, pointProcessUsecase)
            }

            it("성공") {
                val user = userFixture.persist(totalPoints = Point(10))

                every { pointGameProbabilityQueryService.findAll() } returns listOf(
                    PointGameProbabilityFixture.create(point = Point(100), probability = 10000),
                )

                pointGameUsecase(PointGameCommand(user.id))

                verify(exactly = 1) {
                    pointProcessUsecase(
                        withArg { command ->
                            command.userId shouldBe user.id
                            command.point shouldBe Point(100)
                            command.description.shouldNotBeBlank()
                        },
                    )
                }
            }

            it("포인트가 부족해서 실패") {
                val user = userFixture.persist(totalPoints = Point(9))

                every { pointGameProbabilityQueryService.findAll() } returns listOf(
                    PointGameProbabilityFixture.create(point = Point(100), probability = 10000),
                )

                shouldThrow<IllegalArgumentException> {
                    pointGameUsecase(PointGameCommand(user.id))
                }

                verify(exactly = 0) {
                    pointProcessUsecase(any<PointProcessCommand>())
                }
            }

            it("게임 결과과 꽝이라 포인트 지급 안됨") {
                val user = userFixture.persist(totalPoints = Point(10))

                every { pointGameProbabilityQueryService.findAll() } returns listOf(
                    PointGameProbabilityFixture.create(point = Point(0), probability = 10000),
                )

                pointGameUsecase(PointGameCommand(user.id))

                verify(exactly = 0) {
                    pointProcessUsecase(any<PointProcessCommand>())
                }
            }
        }
    },
)
