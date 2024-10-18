package com.jinuk.toy.applicaiton.like.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.like.LikeFixture
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.like.service.LikeQueryService
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomEnum
import com.jinuk.toy.util.faker.randomLong
import com.jinuk.toy.util.faker.randomString
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class AddLikeUseCaseTest(
    private val addLikeUseCase: AddLikeUseCase,
    private val likeFixture: LikeFixture,
    private val likeQueryService: LikeQueryService,
) : IntegrationTest, DescribeSpec(
    {
        describe("좋아요 추가 유스케이스") {
            val userId = faker.randomLong()
            val likeTarget = LikeTarget(faker.randomEnum<LikeType>(), faker.randomString())

            it("좋아요 성공") {
                val command = AddLikeCommand(userId, likeTarget)
                addLikeUseCase(command)

                likeQueryService.existsByUserIdAndTargetTypeAndTargetId(userId, likeTarget) shouldBe true
            }

            it("좋아요 실패 - 이미 좋아요한 상태") {
                likeFixture.persist(userId = userId, targetId = likeTarget.id, targetType = likeTarget.type)

                val command = AddLikeCommand(userId, likeTarget)
                shouldThrow<IllegalArgumentException> {
                    addLikeUseCase(command)
                }
            }
        }
    },
)
