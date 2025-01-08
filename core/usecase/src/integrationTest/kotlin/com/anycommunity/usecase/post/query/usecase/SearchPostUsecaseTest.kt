package com.anycommunity.usecase.post.query.usecase

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.post.PostSearchSortType
import com.anycommunity.definition.post.PostTitle
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.post.port.query.model.SearchPostQuery
import com.anycommunity.usecase.post.usecase.query.SearchPostUsecase

internal class SearchPostUsecaseTest(
    private val searchPostUsecase: SearchPostUsecase,
    private val postFixture: PostFixture,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 검색 유스케이스") {
            it("조회 성공") {
                val user = userFixture.persist()
                val posts =
                    (1..20).map {
                        postFixture.persist(title = PostTitle("title$it"), userId = user.id, commentCount = 0)
                    }
                val postsSize = posts.size

                val query = SearchPostQuery(
                    keyword = "title",
                    page = 1,
                    size = 3,
                    PostSearchSortType.RECENTLY,
                )
                val result = searchPostUsecase(query)

                result.totalElements shouldBe postsSize

                val content = result.content
                content.size shouldBe 3
                content[0].id shouldBe posts[postsSize - 1].id
                content[1].id shouldBe posts[postsSize - 2].id
                content[2].id shouldBe posts[postsSize - 3].id
                content[2].userName shouldBe user.username
            }
        }
    },
)
