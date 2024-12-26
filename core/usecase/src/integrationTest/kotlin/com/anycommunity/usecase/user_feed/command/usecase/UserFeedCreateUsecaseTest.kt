package com.anycommunity.usecase.user_feed.command.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.follow.FollowFixture
import com.anycommunity.domain.user_feed.jpa.UserFeedRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class UserFeedCreateUsecaseTest(
    private val userFeedCreateUsecase: UserFeedCreateUsecase,
    private val userFeedRepository: UserFeedRepository,
    private val followFixture: FollowFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("유저 피드 생성 유스케이스") {
            it("유저 피드 생성") {
                val postId = faker.randomLong()
                val followingUserId = faker.randomLong()
                val follows = (0..9).map { followFixture.persist(followingUserId = followingUserId) }

                userFeedCreateUsecase(UserFeedCreateCommand(postId, followingUserId))

                val userFeeds = userFeedRepository.findByPostId(postId)
                userFeeds.size shouldBe 10
                (0..9).forEach {
                    userFeeds[it].userId shouldBe follows[it].followerUserId
                    userFeeds[it].postId shouldBe postId
                    userFeeds[it].postAuthorId shouldBe followingUserId
                }
            }
        }
    },
)
