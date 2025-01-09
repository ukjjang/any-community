package com.anycommunity.usecase.follow.usecase.query

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.follow.FollowSearchSortType
import com.anycommunity.domain.follow.FollowFixture
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.follow.port.query.model.GetFollowerQuery

class GetFollowerUsecaseTest(
    private val getFollowerUsecase: GetFollowerUsecase,
    private val userFixture: UserFixture,
    private val followFixture: FollowFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("팔로워 검색 유스케이스") {
            it("조회 성공") {
                val followingUser = userFixture.persist()
                val follows =
                    (1..20).map {
                        val followerUser = userFixture.persist()
                        followFixture.persist(followingUserId = followingUser.id, followerUserId = followerUser.id)
                    }
                val followCount = follows.size

                val query = GetFollowerQuery(
                    followingUserId = followingUser.id,
                    page = 1,
                    size = 3,
                    FollowSearchSortType.RECENTLY,
                )
                val result = getFollowerUsecase(query)

                result.totalElements shouldBe followCount

                val content = result.content
                content.size shouldBe 3
                content[0].id shouldBe follows[followCount - 1].followerUserId
                content[1].id shouldBe follows[followCount - 2].followerUserId
                content[2].id shouldBe follows[followCount - 3].followerUserId
            }
        }
    },
)
