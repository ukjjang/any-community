package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GetPostDetailUsecaseTest(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val postFixture: PostFixture,
    private val userFixture: UserFixture,
    private val commentFixture: CommentFixture,
) : IntegrationTest, DescribeSpec(
        {
            describe("게시글 상세 조회 유스케이스") {
                context("게시글 존재") {
                    val postWriter = userFixture.persist()
                    val post = postFixture.persist(userId = postWriter.id)

                    val commentWriter1 = userFixture.persist()
                    val comment1 = commentFixture.persist(postId = post.id, userId = commentWriter1.id)

                    val commentWriter2 = userFixture.persist()
                    val comment2 = commentFixture.persist(postId = post.id, userId = commentWriter2.id)

                    val commentWriter3 = userFixture.persist()
                    val comment3 = commentFixture.persist(postId = post.id, userId = commentWriter3.id)

                    it("조회 성공") {
                        val result = getPostDetailUsecase(GetPostDetailQuery(post.id))

                        result.id shouldBe post.id
                        result.userId shouldBe postWriter.id
                        result.username shouldBe postWriter.username
                        result.title shouldBe post.title
                        result.content shouldBe post.content

                        result.comments[0].id shouldBe comment1.id
                        result.comments[0].userId shouldBe comment1.userId
                        result.comments[0].username shouldBe commentWriter1.username
                        result.comments[0].content shouldBe comment1.content

                        result.comments[1].id shouldBe comment2.id
                        result.comments[1].userId shouldBe comment2.userId
                        result.comments[1].username shouldBe commentWriter2.username
                        result.comments[1].content shouldBe comment2.content

                        result.comments[2].id shouldBe comment3.id
                        result.comments[2].userId shouldBe comment3.userId
                        result.comments[2].username shouldBe commentWriter3.username
                        result.comments[2].content shouldBe comment3.content
                    }

                    it("조회 실패 - 다른 id로 조회") {
                        shouldThrow<NoSuchElementException> {
                            getPostDetailUsecase(GetPostDetailQuery(post.id + 1))
                        }
                    }
                }
            }
        },
    )
