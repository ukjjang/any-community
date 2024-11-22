package com.anycommunity.domain.like

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.like.LikeType
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong
import com.anycommunity.util.faker.randomString

class LikeTargetTest : DescribeSpec(
    {
        describe("LikeTarget") {
            context("type 과 id 의 형태가 일치할때") {
                it("정상적으로 생성된다") {
                    val postLike = LikeTarget.from(type = LikeType.POST, faker.randomLong())
                    postLike.type shouldBe LikeType.POST
                    val commentLike = LikeTarget.from(type = LikeType.COMMENT, faker.randomLong())
                    commentLike.type shouldBe LikeType.COMMENT
                }
            }

            context("type 과 id 의 형태가 불일치할 때") {
                it("IllegalArgumentException이 발생한다") {
                    shouldThrow<IllegalArgumentException> {
                        LikeTarget.from(type = LikeType.POST, faker.randomString())
                    }
                    shouldThrow<IllegalArgumentException> {
                        LikeTarget.from(type = LikeType.COMMENT, faker.randomString())
                    }
                }
            }
        }
    },
)
