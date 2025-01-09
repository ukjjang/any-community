package com.anycommunity.usecase.follow.usecase.command

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowFixture
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.event.UnFollowEvent
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.shared.outbox.OutboxCreator
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.follow.port.command.model.UnFollowCommand
import com.anycommunity.usecase.user.usecase.command.internal.UpdateUserFollowCountUsecase
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class UnFollowUseCaseTest(
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
    private val followFixture: FollowFixture,
    private val userFixture: UserFixture,
    private val followQueryService: FollowQueryService,
    private val outboxCreator: OutboxCreator,
) : IntegrationTest, DescribeSpec(
    {
        val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase = mockk(relaxed = true)
        val outboxCreator: OutboxCreator = mockk(relaxed = true)
        val unFollowUseCase =
            UnFollowUseCase(
                followCommandService,
                userQueryService,
                updateUserFollowCountUsecase,
                outboxCreator,
            )

        describe("언팔로우 유스케이스") {
            beforeTest {
                clearMocks(updateUserFollowCountUsecase, outboxCreator)
            }

            context("a,b,c 유저가 존재하고 a->b 팔로우 상태이다.") {
                val aUser = userFixture.persist()
                val bUser = userFixture.persist()
                val cUser = userFixture.persist()

                followFixture.persist(
                    followerUserId = aUser.id,
                    followingUserId = bUser.id,
                )

                it("a->b 언팔로우 성공") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = bUser.id)
                    val command = UnFollowCommand(relation)
                    unFollowUseCase(command)

                    followQueryService.existsByFollowRelation(relation) shouldBe false

                    verify(exactly = 1) {
                        updateUserFollowCountUsecase(
                            withArg { command ->
                                command.followRelation shouldBe relation
                                command.countOperation shouldBe CountOperation.DECREMENT
                            },
                        )
                    }

                    verify(exactly = 1) {
                        outboxCreator.create(any(), any<UnFollowEvent>())
                    }
                }

                it("언팔로우 실패 - 팔로우 관계가 아님") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = cUser.id)
                    val command = UnFollowCommand(relation)
                    shouldThrow<NoSuchElementException> {
                        unFollowUseCase(command)
                    }

                    verify(exactly = 0) {
                        updateUserFollowCountUsecase(any())
                    }

                    verify(exactly = 0) {
                        outboxCreator.create(any(), any<UnFollowEvent>())
                    }
                }

                it("언팔로우 실패 - 존재하지 않는 대상") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = faker.randomLong())
                    val command = UnFollowCommand(relation)
                    shouldThrow<NoSuchElementException> {
                        unFollowUseCase(command)
                    }

                    verify(exactly = 0) {
                        updateUserFollowCountUsecase(any())
                    }

                    verify(exactly = 0) {
                        outboxCreator.create(any(), any<UnFollowEvent>())
                    }
                }
            }
        }
    },
)
