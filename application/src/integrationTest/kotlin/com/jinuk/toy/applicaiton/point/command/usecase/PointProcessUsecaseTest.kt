package com.jinuk.toy.applicaiton.point.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.common.util.faker.randomString
import com.jinuk.toy.common.value.point.Point
import com.jinuk.toy.domain.user.UserFixture
import com.jinuk.toy.domain.user.jpa.UserRepository

class PointProcessUsecaseTest(
    private val pointProcessUsecase: PointProcessUsecase,
    private val userFixture: UserFixture,
    private val userRepository: UserRepository,
) : IntegrationTest, DescribeSpec({
    describe("포인트 처리 유스케이스") {
        context("유저 존재") {
            val user = userFixture.persist()

            it("포인트 지급에 성공한다") {
                val point = Point(faker.randomLong())
                val command = PointProcessCommand(user.id, point, faker.randomString())
                pointProcessUsecase(command)

                val updatedUser = userRepository.findById(user.id).shouldNotBeNull()
                updatedUser.totalPoints shouldBe user.totalPoints + point
            }
        }
    }
})
