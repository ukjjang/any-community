package com.jinuk.toy.applicaiton.post.query.usecase

import com.jinuk.toy.applicaiton.IntegrationTest
import com.jinuk.toy.domain.comment.CommentFixture
import com.jinuk.toy.domain.like.LikeFixture
import com.jinuk.toy.domain.like.LikeTarget
import com.jinuk.toy.domain.like.LikeType
import com.jinuk.toy.domain.post.PostFixture
import com.jinuk.toy.domain.post.UserFixture
import com.jinuk.toy.domain.post.service.cacheKeyByGetById
import com.jinuk.toy.infra.redis.cache.cacheEvict
import com.jinuk.toy.util.faker.faker
import com.jinuk.toy.util.faker.randomLong
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class GetPostDetailUsecaseTest(
    private val getPostDetailUsecase: GetPostDetailUsecase,
    private val postFixture: PostFixture,
    private val userFixture: UserFixture,
    private val likeFixture: LikeFixture,
    private val commentFixture: CommentFixture,
) : IntegrationTest, DescribeSpec(
        {
            describe("게시글 상세 조회 유스케이스") {
                context("게시글 존재") {
                    val postWriter = userFixture.persist()
                    val viewer = userFixture.persist()

                    val post = postFixture.persist(userId = postWriter.id)
                    cacheEvict(cacheKeyByGetById(post.id))

                    val likeTarget = LikeTarget(LikeType.POST, post.id.toString())
                    likeFixture.persist(targetType = likeTarget.type, targetId = likeTarget.id, userId = viewer.id)
                    likeFixture.persist(targetType = likeTarget.type, targetId = likeTarget.id)
                    likeFixture.persist(targetType = likeTarget.type, targetId = likeTarget.id)
                    likeFixture.persist(targetType = likeTarget.type, targetId = likeTarget.id)

                    commentFixture.persist(postId = post.id)
                    commentFixture.persist(postId = post.id)

                    it("조회 성공") {
                        val result = getPostDetailUsecase(GetPostDetailQuery(post.id, viewer.id))

                        result.id shouldBe post.id
                        result.userId shouldBe postWriter.id
                        result.username shouldBe postWriter.username
                        result.title shouldBe post.title
                        result.content shouldBe post.content
                        result.likeCount shouldBe 4
                        result.commentCount shouldBe 2
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
