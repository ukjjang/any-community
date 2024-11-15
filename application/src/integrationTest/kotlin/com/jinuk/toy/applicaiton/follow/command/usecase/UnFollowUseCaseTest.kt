package com.jinuk.toy.applicaiton.follow.command.usecase

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.common.util.faker.faker
import com.jinuk.toy.common.util.faker.randomLong
import com.jinuk.toy.domain.follow.FollowFixture
import com.jinuk.toy.domain.follow.FollowRelation
import com.jinuk.toy.domain.follow.service.FollowQueryService
import com.jinuk.toy.domain.user.UserFixture

internal class UnFollowUseCaseTest(
    private val unFollowUseCase: UnFollowUseCase,
    private val followFixture: FollowFixture,
    private val userFixture: UserFixture,
    private val followQueryService: FollowQueryService,
) : IntegrationTest, DescribeSpec(
    {
        describe("언팔로우 유스케이스") {
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
                }

                it("언팔로우 실패 - 팔로우 관계가 아님") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = cUser.id)
                    val command = UnFollowCommand(relation)
                    shouldThrow<NoSuchElementException> {
                        unFollowUseCase(command)
                    }
                }

                it("언팔로우 실패 - 존재하지 않는 대상") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = faker.randomLong())
                    val command = UnFollowCommand(relation)
                    shouldThrow<NoSuchElementException> {
                        unFollowUseCase(command)
                    }
                }
            }
        }
    },
)
