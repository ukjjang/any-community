package com.anycommunity.usecase.point.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.point.Point
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.domain.user.jpa.UserRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessCommand
import com.anycommunity.usecase.point.command.usecase.internal.PointProcessUsecase
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomString

class PointProcessUsecaseTest(
    private val pointProcessUsecase: PointProcessUsecase,
    private val userFixture: UserFixture,
    private val userRepository: UserRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("포인트 처리 유스케이스") {
            context("유저 존재") {
                val user = userFixture.persist()

                it("포인트 지급에 성공한다") {
                    val point = Point(20)
                    val command = PointProcessCommand(user.id, point, faker.randomString())
                    pointProcessUsecase(command)

                    val updatedUser = userRepository.findById(user.id).shouldNotBeNull()
                    updatedUser.totalPoints shouldBe user.totalPoints + point
                }
            }
        }
    },
)
