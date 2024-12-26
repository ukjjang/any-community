package com.anycommunity.usecase.user.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.global.CountOperation
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.domain.user.jpa.UserRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.user.command.usecase.internal.UpdateUserFollowCountCommand
import com.anycommunity.usecase.user.command.usecase.internal.UpdateUserFollowCountUsecase

internal class UpdateUserFollowCountUsecaseTest(
    private val updateUserFollowCountUsecase: UpdateUserFollowCountUsecase,
    private val userRepository: UserRepository,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("유저 팔로우 카운트 업데이트 유스케이스") {
            it("팔로우 업데이트") {
                val userA = userFixture.persist(followingCount = 0, followerCount = 0)
                val userB = userFixture.persist(followingCount = 0, followerCount = 0)
                val followRelation = FollowRelation(followerUserId = userA.id, followingUserId = userB.id)

                updateUserFollowCountUsecase(UpdateUserFollowCountCommand(followRelation, CountOperation.INCREASE))
                val userA2 = userRepository.findById(userA.id)
                val userB2 = userRepository.findById(userB.id)
                userA2?.followingCount shouldBe 1
                userA2?.followerCount shouldBe 0
                userB2?.followingCount shouldBe 0
                userB2?.followerCount shouldBe 1

                updateUserFollowCountUsecase(UpdateUserFollowCountCommand(followRelation, CountOperation.DECREMENT))
                val userA3 = userRepository.findById(userA.id)
                val userB3 = userRepository.findById(userB.id)
                userA3?.followingCount shouldBe 0
                userA3?.followerCount shouldBe 0
                userB3?.followingCount shouldBe 0
                userB3?.followerCount shouldBe 0
            }
        }
    },
)
