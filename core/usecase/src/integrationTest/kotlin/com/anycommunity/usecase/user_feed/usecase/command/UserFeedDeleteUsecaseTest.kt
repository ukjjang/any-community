package com.anycommunity.usecase.user_feed.usecase.command

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.follow.FollowRelation
import com.anycommunity.domain.user_feed.UserFeedFixture
import com.anycommunity.domain.user_feed.jpa.UserFeedRepository
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.user_feed.port.command.model.DeleteUserFeedCommandByPostDelete
import com.anycommunity.usecase.user_feed.port.command.model.DeleteUserFeedCommandByUnFollow

internal class UserFeedDeleteUsecaseTest(
    private val deleteUserFeedUsecase: DeleteUserFeedUsecase,
    private val userFeedFixture: UserFeedFixture,
    private val userFeedRepository: UserFeedRepository,
) : IntegrationTest, DescribeSpec(
    {
        describe("유저 피드 삭제 유스케이스") {
            it("유저 피드 삭제 (게시글 id)") {
                val userFeed = userFeedFixture.persist()
                userFeedFixture.persist(postId = userFeed.postId)
                userFeedFixture.persist(postId = userFeed.postId)
                userFeedFixture.persist(postId = userFeed.postId)
                userFeedFixture.persist(postId = userFeed.postId)

                deleteUserFeedUsecase(DeleteUserFeedCommandByPostDelete(userFeed.postId))
                userFeedRepository.findByPostId(userFeed.postId) shouldBe emptyList()
            }

            it("유저 피드 삭제 (언팔로우)") {
                val userFeed = userFeedFixture.persist()
                userFeedFixture.persist(userId = userFeed.userId, postAuthorId = userFeed.postAuthorId)
                userFeedFixture.persist(userId = userFeed.userId, postAuthorId = userFeed.postAuthorId)
                userFeedFixture.persist(userId = userFeed.userId, postAuthorId = userFeed.postAuthorId)
                userFeedFixture.persist(userId = userFeed.userId, postAuthorId = userFeed.postAuthorId)

                val followRelation = FollowRelation(userFeed.userId, userFeed.postAuthorId)
                deleteUserFeedUsecase(DeleteUserFeedCommandByUnFollow(followRelation))

                userFeedRepository.findByUserId(userFeed.userId) shouldBe emptyList()
            }
        }
    },
)
