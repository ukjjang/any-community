package com.jinuk.toy.domain.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.jinuk.toy.common.value.global.CountOperation
import com.jinuk.toy.common.value.point.Point

class UserTest : DescribeSpec(
    {
        describe("팔로잉 카운트 업데이트") {
            it("팔로잉 수를 증가시킨다") {
                val user = UserFixture.create(followingCount = 0)
                val updatedUser = user.updateFollowingCount(CountOperation.INCREASE)
                updatedUser.followingCount shouldBe 1L
            }

            it("팔로잉 수를 감소시킨다") {
                val user = UserFixture.create(followingCount = 4)
                val updatedUser = user.updateFollowingCount(CountOperation.DECREMENT)
                updatedUser.followingCount shouldBe 3L
            }
        }

        describe("팔로우 카운트 업데이트") {
            it("팔로워 수를 증가시킨다") {
                val user = UserFixture.create(followerCount = 111)
                val updatedUser = user.updateFollowerCount(CountOperation.INCREASE)
                updatedUser.followerCount shouldBe 112L
            }

            it("팔로워 수를 감소시킨다") {
                val user = UserFixture.create(followerCount = 111)
                val updatedUser = user.updateFollowerCount(CountOperation.DECREMENT)
                updatedUser.followerCount shouldBe 110L
            }
        }

        describe("포인트 업데이트") {
            it("포인트를 증가시킨다") {
                val user = UserFixture.create(totalPoints = Point(100))
                val updatedUser = user.updateTotalPoints(Point(50))
                updatedUser.totalPoints shouldBe Point(150)
            }

            it("포인트를 감소시킨다") {
                val user = UserFixture.create(totalPoints = Point(100))
                val updatedUser = user.updateTotalPoints(Point(-100))
                updatedUser.totalPoints shouldBe Point.ZERO
            }

            it("포인트가 부족하면 예외를 던진다") {
                val user = UserFixture.create(totalPoints = Point(19))
                shouldThrow<IllegalArgumentException> {
                    user.updateTotalPoints(Point(-20))
                }
            }
        }
    },
)
