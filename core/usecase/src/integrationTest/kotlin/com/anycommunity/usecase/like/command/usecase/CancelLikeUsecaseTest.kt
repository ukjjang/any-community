package com.anycommunity.usecase.like.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.LikeFixture
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.like.service.LikeQueryService
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.like.command.port.command.model.CancelLikeCommand
import com.anycommunity.usecase.like.command.usecase.command.CancelLikeUsecase
import com.anycommunity.usecase.like.command.usecase.command.internal.UpdateLikeCountUsecase
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong

internal class CancelLikeUsecaseTest(
    private val likeFixture: LikeFixture,
    private val likeQueryService: LikeQueryService,
    private val likeCommandService: LikeCommandService,
) : IntegrationTest, DescribeSpec(
    {
        val updateLikeCountUsecase: UpdateLikeCountUsecase = mockk(relaxed = true)
        val cancelLikeUsecase =
            CancelLikeUsecase(likeCommandService, updateLikeCountUsecase)

        describe("좋아요 삭제 유스케이스") {
            beforeTest {
                clearMocks(updateLikeCountUsecase)
            }

            val userId = faker.randomLong()
            val likeTarget = LikeTarget(faker.randomEnum<LikeType>(), faker.randomLong().toString())

            it("좋아요 삭제 성공") {
                likeFixture.persist(userId = userId, targetType = likeTarget.type, targetId = likeTarget.id)

                val command = CancelLikeCommand(userId, likeTarget)
                cancelLikeUsecase(command)

                likeQueryService.existsByUserIdAndTarget(userId, likeTarget) shouldBe false

                verify(exactly = 1) {
                    updateLikeCountUsecase(
                        withArg { command ->
                            command.likeTarget shouldBe likeTarget
                            command.countOperation shouldBe CountOperation.DECREMENT
                        },
                    )
                }
            }

            it("좋아요 삭제 실패 - 좋아요 상태가 아님") {
                val command = CancelLikeCommand(userId, likeTarget)
                shouldThrow<NoSuchElementException> {
                    cancelLikeUsecase(command)
                }

                verify(exactly = 0) {
                    updateLikeCountUsecase(any())
                }
            }
        }
    },
)
