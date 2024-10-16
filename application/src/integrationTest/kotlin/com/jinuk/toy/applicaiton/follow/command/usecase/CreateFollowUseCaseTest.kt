package com.jinuk.toy.applicaiton.follow.command.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.post.FollowFixture
import com.jinuk.toy.domain.post.UserFixture
import com.jinuk.toy.domain.user.FollowRelation
import com.jinuk.toy.domain.user.service.FollowQueryService
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

internal class CreateFollowUseCaseTest(
    private val createFollowUseCase: CreateFollowUseCase,
    private val followFixture: FollowFixture,
    private val userFixture: UserFixture,
    private val followQueryService: FollowQueryService,
) : IntegrationTest, DescribeSpec(
    {
        describe("팔로우 생성 유스케이스") {
            context("a,b,c 유저가 존재하고 a->b 팔로우 상태이다.") {
                val aUser = userFixture.persist()
                val bUser = userFixture.persist()
                val cUser = userFixture.persist()

                followFixture.persist(
                    followerUserId = aUser.id,
                    followingUserId = bUser.id
                )

                it("a->c 팔로우 성공") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = cUser.id)
                    val command = CreateFollowCommand(relation)
                    createFollowUseCase(command)

                    followQueryService.existsByFollowRelation(relation) shouldBe true
                }

                it("생성 실패 - 이미 팔로우 관계") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = bUser.id)
                    val command = CreateFollowCommand(relation)
                    shouldThrow<IllegalArgumentException> {
                        createFollowUseCase(command)
                    }
                }

                it("생성 실패 - 존재하지 않는 팔로우 대상") {
                    val relation = FollowRelation(followerUserId = aUser.id, followingUserId = faker.randomLong())
                    val command = CreateFollowCommand(relation)
                    shouldThrow<NoSuchElementException> {
                        createFollowUseCase(command)
                    }
                }
            }
        }
    },
)
