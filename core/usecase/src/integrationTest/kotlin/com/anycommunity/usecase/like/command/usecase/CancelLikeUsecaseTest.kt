package com.anycommunity.usecase.like.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.LikeFixture
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeQueryService
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong

internal class CancelLikeUsecaseTest(
    private val cancelLikeUseCase: CancelLikeUsecase,
    private val likeFixture: LikeFixture,
    private val likeQueryService: LikeQueryService,
) : IntegrationTest, DescribeSpec(
    {
        describe("좋아요 삭제 유스케이스") {
            val userId = faker.randomLong()
            val likeTarget = LikeTarget(faker.randomEnum<LikeType>(), faker.randomLong().toString())

            it("좋아요 삭제 성공") {
                likeFixture.persist(userId = userId, targetType = likeTarget.type, targetId = likeTarget.id)

                val command = CancelLikeCommand(userId, likeTarget)
                cancelLikeUseCase(command)

                likeQueryService.existsByUserIdAndTarget(userId, likeTarget) shouldBe false
            }

            it("좋아요 삭제 실패 - 좋아요 상태가 아님") {
                val command = CancelLikeCommand(userId, likeTarget)
                shouldThrow<NoSuchElementException> {
                    cancelLikeUseCase(command)
                }
            }
        }
    },
)