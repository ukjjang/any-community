package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.UserFixture
import com.jinuk.toy.domain.post.value.PostTitle
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SearchPostUsecaseTest(
    private val searchPostUsecase: SearchPostUsecase,
    private val postFixture: PostFixture,
    private val commentFixture: CommentFixture,
    private val userFixture: UserFixture,
) : IntegrationTest, DescribeSpec(
    {
        describe("게시글 검색 유스케이스") {
            context("작성자, 게시글, 댓글 존재") {
                val user = userFixture.persist()
                val posts = (1..20).map {
                    postFixture.persist(title = PostTitle("title$it"), userId = user.id)
                }
                val postSize = posts.size

                repeat(5) {
                    commentFixture.persist(postId = posts[postSize-3].id)
                }


                it("조회 성공") {
                    val query = SearchPostQuery(
                        keyword = "title",
                        page = 1,
                        size = 3,
                    )
                    val result = searchPostUsecase(query)

                    result.totalElements shouldBe postSize

                    val content = result.content
                    content.size shouldBe 3

                    content[0].id shouldBe posts[postSize-1].id
                    content[1].id shouldBe posts[postSize-2].id
                    content[2].id shouldBe posts[postSize-3].id
                    content[2].userName shouldBe user.username
                    content[2].commentCount shouldBe 5
                }
            }
        }
    },
)

