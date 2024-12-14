package com.anycommunity.usecase.like.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.global.kafka.KafkaTopic
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.LikeFixture
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.like.event.LikeAddedEvent
import com.anycommunity.domain.like.service.LikeCommandService
import com.anycommunity.domain.like.service.LikeQueryService
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomEnum
import com.anycommunity.util.faker.randomLong

internal class AddLikeUseCaseTest(
    private val likeCommandService: LikeCommandService,
    private val likeFixture: LikeFixture,
    private val likeQueryService: LikeQueryService,
) : IntegrationTest, DescribeSpec(
    {
        val outboxCreator: OutboxCreator = mockk(relaxed = true)
        val addLikeUseCase =
            AddLikeUseCase(likeCommandService, outboxCreator)

        describe("좋아요 추가 유스케이스") {
            beforeTest {
                clearMocks(outboxCreator)
            }

            val userId = faker.randomLong()
            val likeTarget = LikeTarget(faker.randomEnum<LikeType>(), faker.randomLong().toString())

            it("좋아요 성공") {
                val command = AddLikeCommand(userId, likeTarget)
                addLikeUseCase(command)

                likeQueryService.existsByUserIdAndTarget(userId, likeTarget) shouldBe true

                verify(exactly = 1) {
                    outboxCreator.create(KafkaTopic.Like.ADD, any<LikeAddedEvent>())
                }
            }

            it("좋아요 실패 - 이미 좋아요한 상태") {
                likeFixture.persist(userId = userId, targetId = likeTarget.id, targetType = likeTarget.type)

                val command = AddLikeCommand(userId, likeTarget)
                shouldThrow<IllegalArgumentException> {
                    addLikeUseCase(command)
                }

                verify(exactly = 0) {
                    outboxCreator.create(KafkaTopic.Like.ADD, any<LikeAddedEvent>())
                }
            }
        }
    },
)
