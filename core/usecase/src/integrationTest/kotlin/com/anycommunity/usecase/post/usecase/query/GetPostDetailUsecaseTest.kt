package com.anycommunity.usecase.post.usecase.query

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import com.anycommunity.definition.like.LikeType
import com.anycommunity.domain.like.LikeFixture
import com.anycommunity.domain.like.LikeTarget
import com.anycommunity.domain.post.PostFixture
import com.anycommunity.domain.user.UserFixture
import com.anycommunity.usecase.IntegrationTest
import com.anycommunity.usecase.post.port.query.model.GetPostDetailQuery
import com.anycommunity.util.faker.faker
import com.anycommunity.util.faker.randomLong

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

                val post = postFixture.persist(userId = postWriter.id, commentCount = 0)

                val likeTarget = LikeTarget(LikeType.POST, post.id.toString())
                likeFixture.persist(targetType = likeTarget.type, targetId = likeTarget.id, userId = viewer.id)

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
                        getPostDetailUsecase(GetPostDetailQuery(faker.randomLong()))
                    }
                }
            }
        }
    },
)
