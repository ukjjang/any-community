package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.like.LikeFixture
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.UserFixture
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GetPostDetailUsecaseTest(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val postFixture: PostFixture,
    private val userFixture: UserFixture,
    private val likeFixture: LikeFixture,
) : IntegrationTest, DescribeSpec(
        {
            describe("게시글 상세 조회 유스케이스") {
                context("게시글 존재") {
                    val postWriter = userFixture.persist()
                    val viewer = userFixture.persist()

                    val post = postFixture.persist(userId = postWriter.id)

                    likeFixture.persist(targetType = LikeType.POST, targetId = post.id.toString(), userId = viewer.id)

                    it("조회 성공") {
                        val result = getPostDetailUsecase(GetPostDetailQuery(post.id, viewer.id))

                        result.id shouldBe post.id
                        result.userId shouldBe postWriter.id
                        result.username shouldBe postWriter.username
                        result.title shouldBe post.title
                        result.content shouldBe post.content
                        result.isViewerLike shouldBe true
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
