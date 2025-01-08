package com.anycommunity.usecase.follow.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowFixture
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.follow.service.FollowCommandService
import com.anycommunity.domain.follow.service.FollowQueryService
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.domain.user.service.UserQueryService
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.follow.port.command.model.CreateFollowCommand
import com.anycommunity.usecase.follow.usecase.command.CreateFollowUseCase
import com.anycommunity.usecase.user.usecase.command.internal.UpdateUserFollowCountUsecase
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class CreateFollowUseCaseTest(
    private val followFixture: FollowFixture,
    private val userFixture: UserFixture,
    private val followQueryService: FollowQueryService,
    private val followCommandService: FollowCommandService,
    private val userQueryService: UserQueryService,
) : IntegrationTest, DescribeSpec(
    {
        val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase = mockk(relaxed = true)
        val createFollowUseCase =
            CreateFollowUseCase(followCommandService, userQueryService, updateUserFollowCountUsecase)

        describe("팔로우 생성 유스케이스") {
            beforeTest {
                clearMocks(updateUserFollowCountUsecase)
            }

            context("a,b,c 유저가 존재하고 a->b 팔로우 상태이다.") {
                val aUser = userFixture.persist()
                val bUser = userFixture.persist()
                val cUser = userFixture.persist()

                followFixture.persist(
                    followerUserId = aUser.id,
                    followingUserId = bUser.id,
                )

                it("a->c 팔로우 성공") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = cUser.id)
                    val command = CreateFollowCommand(relation)
                    createFollowUseCase(command)

                    followQueryService.existsByFollowRelation(relation) shouldBe true

                    verify(exactly = 1) {
                        updateUserFollowCountUsecase(
                            withArg { command ->
                                command.followRelation shouldBe relation
                                command.countOperation shouldBe CountOperation.INCREASE
                            },
                        )
                    }
                }

                it("생성 실패 - 이미 팔로우 관계") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = bUser.id)
                    val command = CreateFollowCommand(relation)
                    shouldThrow<IllegalArgumentException> {
                        createFollowUseCase(command)
                    }

                    verify(exactly = 0) {
                        updateUserFollowCountUsecase(any())
                    }
                }

                it("생성 실패 - 존재하지 않는 팔로우 대상") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = faker.randomLong())
                    val command = CreateFollowCommand(relation)
                    shouldThrow<NoSuchElementException> {
                        createFollowUseCase(command)
                    }

                    verify(exactly = 0) {
                        updateUserFollowCountUsecase(any())
                    }
                }
            }
        }
    },
)
