package com.anycommunity.usecase.user_feed.usecase.command

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.follow.FollowFixture
import com.anycommunity.domain.user_feed.jpa.UserFeedRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.user_feed.port.command.model.CreateUserFeedCommand
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

internal class UserFeedCreateUsecaseTest(
    private val createUserFeedUsecase: CreateUserFeedUsecase,
    private val userFeedRepository: UserFeedRepository,
    private val followFixture: FollowFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("유저 피드 생성 유스케이스") {
            it("유저 피드 생성") {
                val postId = faker.randomLong()
                val followingUserId = faker.randomLong()
                val follows = (0..9).map { followFixture.persist(followingUserId = followingUserId) }

                createUserFeedUsecase(CreateUserFeedCommand(postId, followingUserId))

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
