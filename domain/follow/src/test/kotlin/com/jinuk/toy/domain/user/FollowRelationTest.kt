package com.jinuk.toy.domain.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class FollowRelationTest : DescribeSpec({
    describe("FollowRelation") {
        context("팔로워와 팔로잉 ID가 다를 때") {
            it("FollowRelation가 정상적으로 생성된다") {
                val followerUserId = 1L
                val followingUserId = 2L

                val followRelation = FollowRelation(followerUserId, followingUserId)

                followRelation.followerUserId shouldBe followerUserId
                followRelation.followingUserId shouldBe followingUserId
            }
        }

        context("팔로워와 팔로잉 ID가 동일할 때") {
            it("IllegalArgumentException이 발생한다") {
                val exception = shouldThrow<IllegalArgumentException> {
                    FollowRelation(1L, 1L)
                }

                exception.message shouldBe "팔로워와 팔로잉 대상은 같을 수 없습니다."
            }
        }
    }
})
