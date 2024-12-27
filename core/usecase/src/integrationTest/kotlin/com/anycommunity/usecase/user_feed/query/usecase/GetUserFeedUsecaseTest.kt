package com.anycommunity.usecase.user_feed.query.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.domain.user_feed.UserFeedFixture
import com.anycommunity.usecase.IntegrationTest

internal class GetUserFeedUsecaseTest(
    private val getUserFeedUsecase: GetUserFeedUsecase,
    private val postFixture: PostFixture,
    private val userFixture: UserFixture,
    private val userFeedFixture: UserFeedFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("유저 피드 조회 유스케이스") {
            it("조회 성공") {
                val user = userFixture.persist()

                val userFeeds = (1..5).map {
                    val author = userFixture.persist()
                    val post = postFixture.persist(userId = author.id)
                    userFeedFixture.persist(userId = user.id, postId = post.id, postAuthorId = post.userId)
                }

                val userFeedSize = userFeeds.size
                val query = GetUserFeedQuery(
                    userId = user.id,
                    page = 1,
                    size = 3,
                )
                val result = getUserFeedUsecase(query)

                result.totalElements shouldBe userFeedSize

                val content = result.content
                content.size shouldBe 3
                content[0].id shouldBe userFeeds[userFeedSize - 1].id
                content[1].id shouldBe userFeeds[userFeedSize - 2].id
                content[2].id shouldBe userFeeds[userFeedSize - 3].id
            }
        }
    },
)
