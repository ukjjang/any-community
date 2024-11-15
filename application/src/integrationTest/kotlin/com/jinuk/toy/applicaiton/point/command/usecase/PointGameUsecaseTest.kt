package com.jinuk.toy.applicaiton.point.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldNotBeBlank
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.point.PointGameProbabilityFixture
import com.jinuk.toy.domain.point.service.PointGameProbabilityQueryService
import com.jinuk.toy.domain.user.UserFixture
import com.jinuk.toy.domain.user.service.UserQueryService

class PointGameUsecaseTest(
    private val userQueryService: UserQueryService,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("포인트 게임 유스케이스") {
            val pointGameProbabilityQueryService: PointGameProbabilityQueryService = mockk(relaxed = true)
            val pointProcessUsecase: PointProcessUsecase = mockk(relaxed = true)
            val pointGameUsecase =
                PointGameUsecase(pointGameProbabilityQueryService, userQueryService, pointProcessUsecase)

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
                        skipLock = true,
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
